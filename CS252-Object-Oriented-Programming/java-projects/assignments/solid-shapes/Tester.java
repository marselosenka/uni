package csd.uoc.gr.A21;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Will generate two json files, j1.json and j2.json
 */
public class Tester {
    public static void main(String[] args) throws IOException {
//        System.out.println(new SolidShape(2,1,5.12));
//        System.out.println(new Sphere(2,1,5.12,10));

        Drawing D1 = new Drawing();
        for(int j=0;j<50;j++) {
            D1.add(new Sphere(2,1,5.12,j+1));
        }
        System.out.println(D1);

        Drawing D2 = new Drawing();
        for(int j=0;j<50;j++) {
            if(j % 2 != 1) {
                D2.add(new Sphere(j,j,5.12,j+1));
            } else {
                D2.add(new Cube(j,j,5.12,j+1));
            }
        }
        System.out.println(D2);
        FileWriter fw1 = new FileWriter("j1.json");
        fw1.write(D1.toJSON());
        fw1.close();

        FileWriter fw2 = new FileWriter("j2.json");
        fw2.write(D2.toJSON());
        fw2.close();
    }
}
