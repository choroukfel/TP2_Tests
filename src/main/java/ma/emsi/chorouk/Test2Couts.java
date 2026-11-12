
package ma.emsi.chorouk;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.data.message.UserMessage;

public class Test2Couts {

    public static void main(String[] args) {

        String apiKey = System.getenv("GEMINI_API_KEY");

        GoogleGenAiChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        String question = "Je dois partir dans 2 jours à Paris ; j'y resterai 3 jours. Est-ce que je dois mettre un parapluie dans mes valises ?";
        ChatResponse response = model.chat(
                UserMessage.from(question)
        );

        System.out.println("Question : " + question);
        System.out.println("Réponse : " + response.aiMessage().text());

        System.out.println("Tokens d'entrée : "
                + response.tokenUsage().inputTokenCount());

        System.out.println("Tokens de sortie : "
                + response.tokenUsage().outputTokenCount());

        System.out.println("Total des tokens : "
                + response.tokenUsage().totalTokenCount());


        int tokensEntree = response.tokenUsage().inputTokenCount();
        int tokensTotal = response.tokenUsage().totalTokenCount();

        int tokensSortieFacturables = tokensTotal - tokensEntree;

        double prixEntree = 0.30;
        double prixSortie = 2.50;

        double cout = (tokensEntree * prixEntree
                + tokensSortieFacturables * prixSortie) / 1_000_000.0;

        long nombreRequetes = (long) Math.ceil(1.0 / cout);

        System.out.printf("Coût estimé : %.8f $%n", cout);
        System.out.println("Requêtes pour atteindre 1 $ : "
                + nombreRequetes);

    }
}
