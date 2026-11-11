package csd.uoc.gr.A21;

/**
 * @author csd4817
 */
public class SolidShape  {
    public static void main(String[] args) {

    }
    private double x,y,z;
    private int numberOfFaces=0;
    private int numberOfVertices=0;
    private int numberOfEdges=0;
    private double volume=0.0;

    SolidShape(double x,double y,double z) {
        setX(x);
        setY(y);
        setZ(z);

    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }
    public void setZ(double z) {
        this.z = z;
    }
    public int getNumberOfFaces() {
        return numberOfFaces;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    public void setNumberOfFaces(int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
    }

    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }
    public int getNumberOfVertices() {
        return numberOfVertices;
    }
    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Solid shape centered at ("+x+","+y+","+z+") with \n" +
                "faces : "+getNumberOfFaces()+" \n"+"vertices : "+getNumberOfVertices()+" \n"+"edges : "
                +getNumberOfEdges()+" \n"+"volume : "+getVolume();
    }
    

}
