package A14;

import java.io.*;
import java.util.Random;
import javax.swing.JFileChooser;

public class Encrypt {

    public static void main(String[] args) throws IOException {
        long start;
        if(args.length == 0) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile(); String filepath =
                        file.getAbsolutePath();
                System.out.println("The path of the selected file is: " + filepath);

                File file2 = new File(file+"encrypted");
                Random r = new Random();
                int n = r.nextInt(8);
                start =  System.nanoTime();
                Transform(file,file2,n);
                System.out.println("Encryption of file: "+file);
                System.out.println("Encrypted file name: "+file2);
                System.out.println("Randomized input was: "+n);
                long end = System.nanoTime();
                System.out.println("Time taken in nanoseconds: "+(end-start));
            }


        } else if (args.length == 2){
            String s = "encrypted"+args[0];
            start =  System.nanoTime();
            Transform(args[0],s, Integer.parseInt(args[1]));
            long end = System.nanoTime();
            System.out.println("Time taken in nanoseconds: "+(end-start));
            if( test(args[0],s,Integer.parseInt(args[1]))) {
                System.out.println("Decrypted file matches the first stage text!");
            } else {
                System.out.println("Encryption went wrong!");
            }

        }


    }

    // (α) Method to encrypt/decrypt a file given the file names as Strings and an integer key
    public static void Transform(String inputFileName, String outputFileName, int inputK) throws IOException {
        FileInputStream FIS = new FileInputStream(inputFileName);
        FileOutputStream FUS = new FileOutputStream(outputFileName);
        int data;
        while((data = FIS.read()) != -1) { // source : https://www.geeksforgeeks.org/java-io-fileinputstream-class-java/

            FUS.write(data + inputK);
        }
        FIS.close();
        FUS.close();

    }


    /**
     *
     * @param in Message to encrypt
     * @param out Destination file
     * @param K Bytes away from encryption
     * @return True or false
     * @throws IOException
     */
    public static boolean test(String in,String out,int K) throws IOException {
        FileInputStream FIS = new FileInputStream(in);
        FileInputStream FUS = new FileInputStream(out);
        int data;
        boolean status = true;
        while(( data = FUS.read()) != -1) { // source : https://www.geeksforgeeks.org/java-io-fileinputstream-class-java/
            int data2 = FIS.read();
            if (data-(K) != data2) {
                status = false;
            }
        }
        if(status == false) {
            return false;
        } else {
           return true;
        }

    }

    public static void Transform(File inputFile, File outputFile, int inputK) throws IOException {

        Transform(inputFile.toString(),outputFile.toString(),inputK);
}

    // Optional: Method to select a file using a graphical file chooser
    public static File selectFile() {
        return null;
    }
}
