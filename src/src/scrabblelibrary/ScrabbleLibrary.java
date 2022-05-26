package scrabblelibrary;

import java.io.BufferedReader;
//import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author pavel
 */
public class ScrabbleLibrary {

    public static void main(String[] args) {

        ClassLoader classLoader = new ScrabbleLibrary().getClass().getClassLoader();
        String fileName = "scrabblelibrary/dictionary.json";
        File file = new File(classLoader.getResource(fileName).getFile());

        JSONParser parser = new JSONParser();
        try {

            // JSONReader stuff
            FileReader reader = new FileReader(file.getAbsolutePath());
            Object obj = parser.parse(reader);
            //converts
            JSONArray jsonObj = (JSONArray) obj;

            //assigns Scrabble library to "words"
            JSONArray words = jsonObj;

            // STORES JSONARRAY WORDS AS ARRAYLIST
            ArrayList<String> list = new ArrayList();
            if (words != null) {
                for (int i = 0; i < words.size(); i++) {
                    list.add(words.get(i).toString());
                }

                // Reader. This line reads the user input.
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                String b = "";
                while ((b = br.readLine()) != null) {

                    /* Checks if the input is letters only. Scrabble does not have numbers and the rest of the code does not know
                how to handle numbers so just get rid of them here with a simple warning.
                     */
                    if (checkForLetters(b) == false) {
                        System.out.println(" " + "Input rules: \n letters only \n more than one letter" + " ");
                    } else if (checkForLetters(b) == true) {
                        String input = b.toLowerCase();

                        for (int i = 0; i < 5; i++) {
                            permutation("", input, i, list);
                        }

                    }
                }

                // Pesky error catcher
            }
        } catch (IOException | ParseException e) {
        }

    }

    private static void permutation(String prefix, String str, int max, ArrayList words) {
        //System.out.println();
        int n = str.length();
        if (prefix.length() == max) {
            possibleCombination(words, prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), max, words);
            }
        }
    }

    public static String possibleCombination(ArrayList<String> a, String b) {
        int start = 0;
        int end = a.size();

        while (true) {
            int mid = (end + start) / 2;
            if (start == mid) {
                return "No possibilities";
            }
            if (a.get(mid).equals(b)) {
                System.out.println(b);
                return b;
            } else if (b.compareTo(a.get(mid)) > 0) {
                start = mid;
            } else {
                end = mid;
            }
        }
    }

    public static boolean checkForLetters(String str) {
        //return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$")) && str.length() = 0);
        return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$")) && (str.length() > 1));

        /*
        ((str != null) && (!str.equals("")) checks if there is existing input
        (str.matches("^[a-zA-Z]*$")) = Checks if input is composed of allowd chars.
      
     
        str.matches("^[a-zA-Z]*$") is using JAVA Regex API
        https://www.javatpoint.com/java-regex
         */
    }
}
