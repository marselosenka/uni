import com.sun.source.tree.AssertTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class TFIDF {

    public static void main(String[] args) throws FileNotFoundException {

        Map<String, Set<String>> queries= new HashMap<>();
        Map<String, ArrayList<String>> docs= new HashMap<>();

        for (int i =1; i<6; i++) {
            File doc = new File("src/documents/q"+i+".txt");
            Set<String> a = new HashSet<>();
            try (Scanner s = new Scanner(new FileReader(doc))) {
                while (s.hasNext()) {
                    a.add(s.next());
                }
            }
            queries.put("q"+i,a);
        }
        for (int i =1; i<9; i++) {
            File doc = new File("src/documents/d"+i+".txt");
            ArrayList<String> a = new ArrayList<>();
            try (Scanner s = new Scanner(new FileReader(doc))) {
                while (s.hasNext()) {
                    a.add(s.next());
                }
            }
            docs.put("d"+i,a);
        }

        TreeSet<String> vocabulary = vocabulary(docs);
        System.out.println("============= Vocabulary of S1 =============");
        System.out.println(vocabulary);
        TreeMap<String,Integer> dF = dF(docs,vocabulary);
        System.out.println("================ dF of S1 ==================");
        System.out.println(dF);
        Map<String,Map<String,Double>> TF = TF(docs,vocabulary);
        System.out.println("================ TF of S1 ==================");
        System.out.println(TF);
        Map<String,Double> IDF = IDF(dF,docs.size());
        System.out.println("=============== IDF of S1 ==================");
        System.out.println(IDF);
        Map<String,Map<String,Double>> TF_IDF = TF_IDF(TF,IDF); // a
        System.out.println("============== TF*IDF of S1 ================");
        System.out.println(TF_IDF);
        Map<String,Double> cosSim = cosSim(queries,IDF,TF,docs); // b
        System.out.println("========= cosSim of Queries ================");
        System.out.println(cosSim);
        Map<String,Map<String,Double>> S2 = S2(docs,queries);
        System.out.println("========= S2 results ================");
        System.out.println(S2);
        Map<String,Map<String,Double>> S3 = S3(docs,queries);
        System.out.println("========= S3 results ================");
        System.out.println(S3);
    }

    /**
     *
     * @param docs Document files
     * @param queries Query
     * @return Returns Mapping of intersection results of document(i) and query(j).
     */
    public static Map<String,Map<String,Double>> S2(Map<String, ArrayList<String>> docs,Map<String, Set<String>> queries) {
        Map<String,Map<String,Double>> S2 = new HashMap<>();
        for(Map.Entry<String,Set<String>> entry : queries.entrySet()) {
            Map<String,Double> temp = new HashMap<>();
            for(Map.Entry<String,ArrayList<String>> entry2 : docs.entrySet()) {
                Set<String> result = entry.getValue().stream()
                        .filter(entry2.getValue()::contains)
                        .collect(Collectors.toSet());
                Set<String> commonElements = new HashSet(result);
                commonElements.retainAll(entry.getValue());
                temp.put(entry2.getKey(),(double)commonElements.size());

            }
            S2.put(entry.getKey(),temp);
        }
        return S2;
    }

    /**
     *
     * @param docs Map of Document files(Filename,List)
     * @param queries Map of Queries(Query Name,Set)
     * @return Mapping of intersection results of document(i) and query(j) divided their union.
     */
    public static Map<String,Map<String,Double>> S3(Map<String, ArrayList<String>> docs,Map<String, Set<String>> queries) {
        Map<String,Map<String,Double>> S3 = new HashMap<>();
        for(Map.Entry<String,Set<String>> entry : queries.entrySet()) {
            Map<String,Double> temp = new HashMap<>();
            for(Map.Entry<String,ArrayList<String>> entry2 : docs.entrySet()) {
                Set<String> result1 = entry.getValue().stream()
                        .filter(entry2.getValue()::contains)
                        .collect(Collectors.toSet());
                Set<String> commonElements = new HashSet(result1);
                commonElements.retainAll(entry.getValue());

                Set<String> result2 = new HashSet<>();
                result2.addAll(entry.getValue());
                result2.addAll(entry2.getValue());
                temp.put(entry2.getKey(),(double)commonElements.size()/result2.size());

            }
            S3.put(entry.getKey(),temp);
        }
        return S3;
    }

    /**
     *
     * @param queries Map of Queries
     * @param IDF   IDF of document files
     * @param TF    TF of document files
     * @param docs  Document files
     * @return  Returns a mapping of each word of query assigned their cosine similarity value.
     */
    public static Map<String,Double> cosSim(Map<String, Set<String>> queries,
                                Map<String,Double> IDF,
                                Map<String,Map<String,Double>> TF,
                                Map<String, ArrayList<String>> docs) {
        Map<String,Double> cosSim = new HashMap<>();
        Map<String,Map<String,Double>> q_TFIDF = new HashMap<>();
        for(Map.Entry<String,Set<String>> entry : queries.entrySet()) {
            Set<String> query =entry.getValue();
            Map<String,Map<String,Double>> q_TF = TF(docs,query);
            q_TFIDF.putAll(TF_IDF(q_TF,IDF));
        }
        for(Map.Entry<String,Map<String,Double>> entry : q_TFIDF.entrySet()) {
            double sum1 = 0,sum2 = 0;
            double wij,wiq;
            double wij2,wiq2;
            for(Map.Entry<String,Double> entry2 : entry.getValue().entrySet()) {
                    wij = TF.get(entry2.getKey()).get(entry.getKey());
                    wiq = entry2.getValue();
                    sum1 += wij * wiq;
                    wij2 = TF.get(entry2.getKey()).get(entry.getKey())*2;
                    wiq2 = entry2.getValue()*2;
                    sum2 += Math.sqrt(wij2*wiq2);


            }
            cosSim.put(entry.getKey(),sum1/sum2);
        }
        return cosSim;
    }

    /**
     *
     * @param TF    TF of document files.
     * @param IDF   IDF of document files.
     * @return  Mapping of TF*IDF values.
     */
    public static Map<String,Map<String,Double>> TF_IDF(Map<String,Map<String,Double>> TF, Map<String,Double> IDF) {
        Map<String,Map<String,Double>> TF_IDF = new HashMap<>();
        for(Map.Entry<String,Map<String,Double>> entry : TF.entrySet()) {
            Map<String,Double> temp1 = new HashMap<>();
            String doc_name = entry.getKey();

            for(Map.Entry<String,Double> entry2 : entry.getValue().entrySet()) {
                if (!TF_IDF.containsKey(entry2.getKey())) {
                    TF_IDF.put(entry2.getKey(), new HashMap<>());
                } else {
                    TF_IDF.get(entry2.getKey()).put(doc_name,(entry2.getValue()*IDF.get(entry2.getKey())));
                }

            }
            if(!temp1.isEmpty()) {
                TF_IDF.put(entry.getKey(),temp1);
            }

        }
        return TF_IDF;

    }

    /**
     *
     * @param docs Map of documents and an ArrayList of its words.
     * @return TreeSet of all words found on documents.
     */
    public static TreeSet<String> vocabulary(Map<String,ArrayList<String>> docs) {
        TreeSet<String> vocabulary = new TreeSet<>();
        for(Map.Entry<String,ArrayList<String>> entry : docs.entrySet()) {
            ArrayList<String> words = entry.getValue();
                    vocabulary.addAll(words);
        }
        return vocabulary;
    }

    /**
     *
     * @param docs Map of documents and an ArrayList of its words.
     * @param vocabulary Treeset of all words found on documents
     * @return TreeMap that assigns each word and how many files include it.
     */
    public static TreeMap<String,Integer> dF(Map<String,ArrayList<String>> docs,TreeSet<String> vocabulary) {
        TreeMap<String,Integer> dF = new TreeMap<>();
        for(Map.Entry<String,ArrayList<String>> entry : docs.entrySet()) {
            ArrayList<String> seenWords = new ArrayList<>();
            ArrayList<String> words = entry.getValue();
            for(String word : words) {
                if(dF.containsKey(word) && !seenWords.contains(word)) {
                    seenWords.add(word);
                    dF.put(word,dF.get(word)+1);
                } else if(!dF.containsKey(word)) {
                    seenWords.add(word);
                    dF.put(word,1);
                }
            }
        }
        return dF;
    }

    /**
     *
     * @param dF    dF value of document files
     * @param num   number of total files.
     * @return Map of IDF values.
     */
    public static Map<String,Double> IDF(TreeMap<String,Integer> dF,int num) {
        Map<String,Double> IDF = new HashMap<>();

        for(Map.Entry<String,Integer> entry : dF.entrySet()) {
            double idf = Math.log(num/entry.getValue());
            IDF.put(entry.getKey(),idf);
        }
        return IDF;
    }

    /**
     *
     * @param docs  Document file
     * @param vocabulary    Words that are met on the document files.
     * @return  Map of TF values.
     */
    public static Map<String,Map<String,Double>> TF(Map<String, ArrayList<String>> docs,Set<String> vocabulary) {
        Map<String,Map<String,Double>> TF= new HashMap<>();
        for(Map.Entry<String,ArrayList<String>> entry : docs.entrySet()) {
            double max = getMax(entry.getValue());
            //System.out.println("Maximum frequency of file "+entry.getKey()+" is: "+max);
            Map<String,Double> temp = new HashMap<>();
            for(String word : vocabulary) {
                double reps = (double)Collections.frequency(entry.getValue(),word)/max;
                temp.put(word,reps);
            }
            TF.put(entry.getKey(),temp);
        }
        return TF;
    }

    /**
     * Helping method to calculate TF
     * @param entry
     * @return  Returns max value of a term in a file
     */
    private static double getMax(ArrayList<String> entry) {
        Map<String, Integer> freqs = new HashMap<>();
        for (String token : entry) {
            if (freqs.containsKey(token)) {
                freqs.put(token, freqs.get(token) + 1);
            } else {
                freqs.put(token, 1);
            }
        }
        double max = 0;
        for (Map.Entry<String, Integer> entry2 : freqs.entrySet()) {
            if (entry2.getValue() > max) {
                max = entry2.getValue();
            }
        }
        return max;
    }
}

