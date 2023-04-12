/**
 *
 * @author Slam
 */
import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class OpenNLP {

    public static void main(String[] args) {
        System.out.println("This example forms a sentence with keywords base on English");
        // Lista de palabras clave
        List<String> palabrasClave = new ArrayList<>();
        palabrasClave.add("cat");
        palabrasClave.add("play");
        palabrasClave.add("ball");

        // Cargar el modelo de POS tagging
        try {
            // es-pos-maxent.bin for Spanish
            FileInputStream modelIn = new FileInputStream("en-pos-maxent.bin");
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);

            // Identificar el tipo de cada palabra clave
            String[] words = palabrasClave.toArray(new String[palabrasClave.size()]);
            String[] tags = tagger.tag(words);

            // Generar la oración
            String oracion = "";
            Random rand = new Random();
            switch (tags[0]) {
                case "NN":
                    // Sustantivo como sujeto
                    oracion += "The " + words[0] + " ";
                    switch (tags[1]) {
                        case "VB":
                            // Verbo
                            oracion += words[1] + " ";
                            switch (tags[2]) {
                                case "NN":
                                    // Objeto directo
                                    oracion += "with the " + words[2];
                                    break;
                                default:
                                    oracion = "No se puede generar una oración con estas palabras clave";
                                    break;
                            }
                            break;
                        default:
                            oracion = "No se puede generar una oración con estas palabras clave";
                            break;
                    }
                    break;
                default:
                    oracion = "No se puede generar una oración con estas palabras clave";
                    break;
            }
            System.out.println(oracion);

        } catch (IOException e) {
            System.out.println("Error al cargar el modelo de POS tagging");
            e.printStackTrace();
        }
    }
}
