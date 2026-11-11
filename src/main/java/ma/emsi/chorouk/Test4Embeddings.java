
package ma.emsi.chorouk;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel.TaskTypeEnum;
import java.time.Duration;

public class Test4Embeddings {

    public static void main(String[] args) {

        EmbeddingModel model = new BgeSmallEnV15EmbeddingModel();


        EmbeddingModel modelGoogle = GoogleGenAiEmbeddingModel.builder()
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .modelName("gemini-embedding-001")
                .taskType(TaskTypeEnum.SEMANTIC_SIMILARITY)
                .timeout(Duration.ofSeconds(10))
                .build();

        String[][] couples = {
                {"Le chat dort sur le canapé.",
                        "Un félin se repose sur le sofa."},

                {"Il fait très chaud aujourd'hui.",
                        "Il fait extrêmement froid aujourd'hui."},

                {"J'aime apprendre la programmation.",
                        "J'adore étudier le développement informatique."},

                {"La voiture roule sur la route.",
                        "Le soleil brille dans le ciel."},

                {"Je vais acheter du pain.",
                        "Je me rends à la boulangerie pour acheter du pain."},

                {"Le médecin soigne un patient.",
                        "Un docteur examine une personne malade."}
        };

        System.out.println("=== Modèle local BGE Small EN V1.5 ===");

        for (int i = 0; i < couples.length; i++) {

            String phrase1 = couples[i][0];
            String phrase2 = couples[i][1];

            Embedding embedding1 = model.embed(phrase1).content();
            Embedding embedding2 = model.embed(phrase2).content();

            double similarite =
                    CosineSimilarity.between(embedding1, embedding2);

            System.out.println();
            System.out.println("Couple " + (i + 1));
            System.out.println("Phrase 1 : " + phrase1);
            System.out.println("Phrase 2 : " + phrase2);
            System.out.printf("Similarité : %.4f%n", similarite);

            Embedding embeddingGoogle1 = modelGoogle.embed(phrase1).content();
            Embedding embeddingGoogle2 = modelGoogle.embed(phrase2).content();

            double similariteGoogle =
                    CosineSimilarity.between(embeddingGoogle1, embeddingGoogle2);

            System.out.printf("Similarité Google : %.4f%n", similariteGoogle);

        }
    }
}
