package A1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class SuffixTrieNode {

   // final static int MAX_CHAR = 256;

    Map<Character,SuffixTrieNode> children;
    List<Integer> indexes;

    SuffixTrieNode() // Constructor
    {
        // Create an empty linked list for indexes of
        // suffixes starting from this node
        indexes = new LinkedList<Integer>();

        // Initialize all child pointers as NULL
       children = new HashMap<>();
    }

    // A recursive function to insert a suffix of
    // the text in subtree rooted with this node
    void insertSuffix(String s, int index) {

        // Store index in linked list
        indexes.add(index);

        // If string has more characters
        if (s.length() > 0) {

            // Find the first character
            char cIndex = s.charAt(0);

            // If there is no edge for this character,
            // add a new edge
            if (children.get(cIndex) == null)
                children.put(cIndex, new SuffixTrieNode());

            // Recur for next suffix
            children.get(cIndex).insertSuffix(s.substring(1),
                    index + 1);
        }
    }

    // A function to search a pattern in subtree rooted
    // with this node.The function returns pointer to a
    // linked list containing all indexes where pattern
    // is present. The returned indexes are indexes of
    // last characters of matched text.
    List<Integer> search(String s) {

        // If all characters of pattern have been
        // processed,
        if (s.length() == 0)
            return indexes;

        // if there is an edge from the current node of
        // suffix tree, follow the edge.
        if (children.get(s.charAt(0)) != null)
            return (children.get(s.charAt(0)).search(s.substring(1)));

            // If there is no edge, pattern doesnt exist in
            // text
        else
            return null;
    }
}

// A Trie of all suffixes
class Suffix_tree{

    SuffixTrieNode root = new SuffixTrieNode();

    // Constructor (Builds a trie of suffixes of the
    // given text)
    Suffix_tree(String txt) {

        // Consider all suffixes of given string and
        // insert them into the Suffix Trie using
        // recursive function insertSuffix() in
        // A1.SuffixTrieNode class
        for (int i = 0; i < txt.length(); i++)
            root.insertSuffix(txt.substring(i), i);
    }

    /* Prints all occurrences of pat in the Suffix Trie S
    (built for text) */
    void search_tree(String pat) {

        // Let us call recursive search function for
        // root of Trie.
        // We get a list of all indexes (where pat is
        // present in text) in variable 'result'
        List<Integer> result = root.search(pat);

        // Check if the list of indexes is empty or not
        if (result == null)
            System.out.println("Pattern not found");
        else {

            int patLen = pat.length();

            for (Integer i : result)
                System.out.println("Pattern found at position " +
                        (i - patLen));
        }
    }

    // driver program to test above functions
    public static void main(String args[]) throws FileNotFoundException {
        String path = args[0];
        String text = "";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line;
            }
        } catch (IOException e) {
            System.out.println("Error on reading file..");
            return;
        }
        // Let us build a suffix trie for text
        // "geeksforgeeks.org"
        Suffix_tree S = new Suffix_tree(text);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a query (or exit to terminate)");
            String query = scanner.nextLine();

            if (query.equals("exit")) {
                break;
            }

            System.out.println("\nSearch for: " + query);
            S.search_tree(query);
        }
    }
}
// This code is contributed by Sumit Ghosh