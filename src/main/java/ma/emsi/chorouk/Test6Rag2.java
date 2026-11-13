
package ma.emsi.chorouk;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import java.util.Scanner;

/**
 * Test 6
 */
public class Test6Rag2 {

    // Interface de l'assistant conversationnel
    interface Assistant {
        String chat(String userMessage);
    }

    public static void main(String[] args) {

        // Récupération de la clé API
        String llmKey = System.getenv("GEMINI_API_KEY");

        if (llmKey == null || llmKey.isBlank()) {
            System.out.println(
                    "La variable d'environnement GEMINI_API_KEY n'est pas définie."
            );
            return;
        }

        // Création du modèle Gemini avec une température faible
        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(llmKey)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.3)
                .build();

        // Chargement du document
        String nomDocument = "ml.pdf";
        Document document = FileSystemDocumentLoader.loadDocument(
                nomDocument,
                new ApachePdfBoxDocumentParser()
         );
        // Base vectorielle en mémoire
        EmbeddingStore<TextSegment> embeddingStore =
                new InMemoryEmbeddingStore<>();

        // Phase 1 : ingestion du document
        EmbeddingStoreIngestor.ingest(document, embeddingStore);

        // Création de l'assistant avec mémoire et RAG
        Assistant assistant =
                AiServices.builder(Assistant.class)
                        .chatModel(model)
                        .chatMemory(
                                MessageWindowChatMemory.withMaxMessages(10)
                        )
                        .contentRetriever(
                                EmbeddingStoreContentRetriever.from(embeddingStore)
                        )
                        .build();

        // Phase 2 : question au LLM

        // Conversation avec l'assistant
        System.out.println("=== Assistant RAG avec ml.pdf ===");
        System.out.println("Posez vos questions sur le cours.");
        System.out.println("Tapez 'fin' pour terminer.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("==================================================");
                System.out.println("Posez votre question : ");
                String question = scanner.nextLine();

                if (question.isBlank()) {
                    continue;
                }

                System.out.println("==================================================");

                if ("fin".equalsIgnoreCase(question)) {
                    break;
                }

                String reponse = assistant.chat(question);
                System.out.println("Assistant : " + reponse);
                System.out.println("==================================================");
            }
        }
    }
}
