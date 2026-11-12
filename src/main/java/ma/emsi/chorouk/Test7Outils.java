
package ma.emsi.chorouk;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import ma.emsi.chorouk.outil.meteo.MeteoTool;

import java.util.Scanner;

public class Test7Outils {

    public static void main(String[] args) {

        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Erreur : GEMINI_API_KEY non configurée.");
            return;
        }

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.3)
                .logRequests(true)
                .logResponses(true)
                .build();

        AssistantMeteo assistant =
                AiServices.builder(AssistantMeteo.class)
                        .chatModel(model)
                        .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                        .tools(new MeteoTool())
                        .build();

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Assistant météo avec LangChain4j ===");
        System.out.println("Posez vos questions.");
        System.out.println("Tapez 'quit' pour terminer.");

        while (true) {

            System.out.print("\nVotre question : ");
            String question = scanner.nextLine();

            if (question.equalsIgnoreCase("quit")) {
                System.out.println("Fin de la conversation.");
                break;
            }

            if (question.isBlank()) {
                continue;
            }

            try {
                String reponse = assistant.chat(question);
                System.out.println("\nRéponse : " + reponse);
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }

        scanner.close();
    }
}
