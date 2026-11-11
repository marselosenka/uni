package A12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.jfugue.Player;
public class Palindromikes {
    public static void main(String[] args) throws FileNotFoundException {
//        Scanner in = new Scanner(System.in);
//        String word = in.nextLine();
//        PalindromikesFraseis P = new PalindromikesFraseis();
//        String s = String.valueOf(P.isPalindromikiFrash(word));

//        PalindromikesLexikou PL = new PalindromikesLexikou("src/A12/Resources/gr.dic");
//        List<String> Pal = PL.Palindromikes_onFile();
//
//        PalindromikesMusic PM = new PalindromikesMusic(Pal);
//        PM.Music_prod();
    }
}

// Class for palindrome phrase functionality
class PalindromikesFraseis {
    /**
     * Constructor, no use.
     */
    PalindromikesFraseis() {
        return;
    }

    /**
     *
     * @param s A word.
     * @return true if it is palindromic, false if not.
     */
    static boolean isPalindromikiFrash(String s) {
        StringBuilder s_rev = new StringBuilder();
        StringBuilder s_alphs = new StringBuilder();
        char c;


        for (int i=0; i < s.length(); i++) {
                c = s.charAt(i);

                if((c >= 'a' && c <= 'z' )|| (c >= 'A' && c <= 'Z')) {
                    s_alphs.append(c);
                    s_rev.insert(0, c);
                } else if((c >= 'α' && c <= 'ω' )||( c >= 'Α' && c <= 'Ω')) {
                    s_alphs.append(c);
                    s_rev.insert(0, c);
                }

        }
        if (s_alphs.toString().equalsIgnoreCase(s_rev.toString()) && !s_alphs.toString().endsWith("ς")) {
            // System.out.println("Einai palindromikh");
            return true;
        } else {
            return false;
        }
    }

}

/**
 *
 */
class PalindromikesLexikou {
    private static FileReader fr;
    private static List<String> Palindromikes_Lekseis = new ArrayList<>();

    /**
     * Scans a file and finds all palindromic words.
     * @return A List of the Palindromic words on a file.
     */
    public static List<String> Palindromikes_onFile() {
        long start_time = System.nanoTime();
        PalindromikesFraseis P = new PalindromikesFraseis();
        int words_total = 0;
        int words_total_length = 0;
        int palindromikes = 0;
        try( Scanner sc=new Scanner(fr) ) {
            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                if(!word.matches("[0-9]+")) {
                    words_total += 1;
                    words_total_length += word.length();
                    String word1 = word;
                    word = Normalizer.normalize(word,
                            Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                    boolean b = P.isPalindromikiFrash(word);
                    if (b) {
                        palindromikes++;
                        Palindromikes_Lekseis.add(word1);
                    }
                }
            }
            long end_time = System.nanoTime();
            System.out.println(end_time - start_time+" Nanoseconds");
            sc.close();
        } catch(Exception e) {
            System.out.println("Sumn wrong");
        }

        System.out.println("Plithos Leksewn: "+words_total);
        System.out.println("Plithos palindromikwn Leksewn: "+palindromikes);
        System.out.println("Average word size Epi Synolou: "+words_total_length/words_total);
        System.out.println("Palindromikes Epi Synolou: "+((double) palindromikes / words_total) * 100+" %");
        Collections.reverse(Palindromikes_Lekseis);
        System.out.println(Palindromikes_Lekseis);
        return Palindromikes_Lekseis;
    }

    /**
     *
     * @param path Initialization of the file path.
     * @throws FileNotFoundException
     */
    PalindromikesLexikou(String path) throws FileNotFoundException {
        fr = new FileReader(path);
    }


}

// Class for converting palindromic words into music
class PalindromikesMusic {
    private static List Words;
    PalindromikesMusic(List Palindromikes_Lekseis) {
        this.Words = Palindromikes_Lekseis;
    }

    /**
     * Matches letter by letter a word to a sound (After the notes of number 60).
     * a is 84, b is 83 .. omega is 60.
     */

    public static void Music_prod() {

        Player player = new Player();
        for( int i =0; i < Words.size(); i++) {
            String s = (String) Words.get(i);
            System.out.print("word = "+s+" ");
            s = Normalizer.normalize(s,
                    Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            System.out.print("normalized word = "+s+" ");
            System.out.print("music score = ");
            for (int j =0; j < s.length(); j++ ) {

                char c = s.charAt(j);
                int y = 60+ (969 - c);
                System.out.print(" ["+y+"]"+c );
                player.play("["+y+"]");

            }
            System.out.println();

        }

    }
}
