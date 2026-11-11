package csd.uoc.gr.A24;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import csd.uoc.gr.A24.Utils;

import javax.swing.*;

public class FileStats {
    static int directory_count = 0;
    static int file_count = 0;
    static ArrayList<String> images = new ArrayList<>();
    public static void listFilesForFolder(File folder) {

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                directory_count++;
                listFilesForFolder(fileEntry);
            } else {
                file_count++;
                if(fileEntry.getName().endsWith(".jpg") || fileEntry.getName().endsWith("png")) {
                    images.add(fileEntry.getAbsolutePath());
                }
                //System.out.println(fileEntry.getAbsolutePath());
            }
        }

    }

    public static void createHTML() throws FileNotFoundException {
        if (!images.isEmpty()) {
            String s = new String();
            File f = new File("src/csd/uoc/gr/A24/images.html");
            PrintWriter pw = new PrintWriter(f);
            pw.write("<!DOCTYPE html>\n");
            pw.write("<html><body>\n");
            for(int i=0;i<images.size();i++) {
                pw.write("<img src=\""+images.get(i)+"\"alt=\""+images.get(i)+"\" height=\"100\">\n");
                System.out.println(images.get(i));
            }
            pw.write("<body><html>\n");
            pw.close();
        } else {
            System.out.println("No images found!");
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
       // File folder = new File("C:\\temp\\");
        //listFilesForFolder(folder);
        Utils U = new Utils();
        String a = Utils.selectAFolderDialog(JFileChooser.DIRECTORIES_ONLY);
        File folder = new File(a);
        listFilesForFolder(folder);
        createHTML();
        System.out.println("Found "+directory_count+" directories");
        System.out.println("Found "+file_count+" files");

        GUI();
    }

    public static void GUI() {
        if (!images.isEmpty()) {
            JFrame win = new JFrame("Images");
            win.setSize(1000, 700);
            win.setLayout(new BorderLayout());
            win.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(((int) images.size()), 4));
            for (int i = 0; i < images.size(); i++) {
                JButton button1 = new JButton(images.get(i));
                JButton button2 = new JButton();
                button1.setPreferredSize(new Dimension(200, 200));
                button2.setPreferredSize(new Dimension(200, 200));
                button2.setIcon(new ImageIcon(images.get(i)));
                panel.add(button1);
                panel.add(button2);

            }
            JScrollPane s = new JScrollPane(panel);
            s.setPreferredSize(new Dimension(200, 200));
            win.add(s);
            win.setVisible(true);
        }
    }
}
