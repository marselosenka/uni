package csd.uoc.gr.A21;

import java.awt.*;

/**
 * @author csd4817
 */
public class Drawing {
    private static SolidShape[] S;
    private static int total_faces;
    private static int total_vertices;
    private static int total_edges;
    private static double total_volume;
    private int capacity;


    Drawing() {
        S = new SolidShape[1000];
        capacity = 0;
        total_faces = 0;
        total_vertices = 0;
        total_edges = 0;
        total_volume = 0.0;
    }

    public void add(SolidShape s) {
        if(capacity<1000) {
            S[capacity] = s;
            total_faces += s.getNumberOfFaces();
            total_vertices += s.getNumberOfVertices();
            total_edges += s.getNumberOfEdges();
            total_volume += s.getVolume();

            capacity++;

        } else {
            System.out.println("No more capacity!!");
        }
    }

    @Override
    public String toString() {
        return "Number of shapes: " + capacity + "\n" + "Total faces: " + total_faces + "\n"+
                "Total vertices: "+total_vertices+"\n"+"Total edges: "+total_edges+"\n"+
                "Total volume: "+total_volume;
    }
    public String toJSON() {
        String s;
        s = "{\n\"drawing\": [\n";
        for(int i =0; i<capacity;i++) {
            s += "{\n";
            s += "\"x\":"+S[i].getX()+",\n";
            s += "\"y\":"+S[i].getY()+",\n";
            if(S[i] instanceof Cube) {
                s += "\"type\":"+"\"cube\""+",\n";
                s += "\"radius\":"+((Cube) S[i]).getSide()+"\n";
            } else if (S[i] instanceof Sphere) {
                s += "\"type\":"+"\"sphere\""+",\n";
                s += "\"radius\":"+((Sphere) S[i]).getRadius()+"\n";
            } else if (S[i] instanceof Cylinder) {
                s += "\"type\":"+"\"cylinder\""+","+"\n";
            }
            if(i == capacity-1) {
                s += "\n}";
            } else {
                s += "\n},\n";
            }
        }
        s += "]}";

        return s;
        }
}
