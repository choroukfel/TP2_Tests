
package ma.emsi.chorouk;

import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.chat.ChatModel;

public class Test1 {

    public static void main(String[] args) {

        String apiKey = System.getenv("GEMINI_API_KEY");

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.5-flash")
                .temperature(0.7)
                .build();


        String question1 = "Bonjour, je m'appelle Chorouk.";
        String reponse1 = model.chat(question1);

        System.out.println("Question 1 : " + question1);
        System.out.println("Réponse 1 : " + reponse1);

        String question2 = "Comment je m'appelle ?";
        String reponse2 = model.chat(question2);

        System.out.println("Question 2 : " + question2);
        System.out.println("Réponse 2 : " + reponse2);

    }
}
