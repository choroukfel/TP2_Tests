
package ma.emsi.chorouk;

import ma.emsi.chorouk.outil.meteo.MeteoTool;

public class TestMeteo {

    public static void main(String[] args) {

        MeteoTool meteo = new MeteoTool();

        // Coordonnées de Paris
        double latitude = 48.85;
        double longitude = 2.35;

        // Séjour de 3 jours à partir d'aujourd'hui
        boolean pluie = meteo.isPluiePrevue(
                latitude,
                longitude,
                0,
                3
        );

        System.out.println("Ville : Paris");
        System.out.println("Durée du séjour : 3 jours");

        if (pluie) {
            System.out.println("Pluie prévue : OUI");
            System.out.println("Conseil : prenez un parapluie !");
        } else {
            System.out.println("Pluie prévue : NON");
            System.out.println("Conseil : parapluie non nécessaire selon les prévisions.");
        }
    }
}
