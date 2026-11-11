
package ma.emsi.chorouk;

import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

public class Test3Traducteur {

    public static void main(String[] args) {

        String apiKey = System.getenv("GEMINI_API_KEY");

        GoogleGenAiChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        // Première partie : une seule variable
        PromptTemplate template = PromptTemplate.from(
                "Traduis le texte suivant en anglais : {{texte}}"
        );

        Prompt prompt = template.apply(
                Map.of("texte", "Bonjour, comment allez-vous ?")
        );

        String reponse = model.chat(prompt.text());

        System.out.println("=== Traduction en anglais ===");
        System.out.println("Texte original : Bonjour, comment allez-vous ?");
        System.out.println("Traduction : " + reponse);


        // Deuxième partie : deux variables
        PromptTemplate template2 = PromptTemplate.from(
                "Traduis le texte suivant en {{langue}} : {{texte}}"
        );

        Prompt prompt2 = template2.apply(
                Map.of(
                        "langue", "espagnol",
                        "texte", "Bonjour, comment allez-vous ?"
                )
        );

        String reponse2 = model.chat(prompt2.text());

        System.out.println();
        System.out.println("=== Traduction avec deux paramètres ===");
        System.out.println("Langue : espagnol");
        System.out.println("Texte original : Bonjour, comment allez-vous ?");
        System.out.println("Traduction : " + reponse2);

    }
}
