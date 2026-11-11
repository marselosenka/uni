package csd.uoc.gr.A21;

/**
 * @author csd4817
 */
public class Sphere extends SolidShape {
    private double radius;
    Sphere(double x,double y,double z,int radius) {
        super(x,y,z);
        setRadius(radius);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    @Override
    public int getNumberOfFaces(){
        return 1;
    }

    @Override
    public double getVolume() {
        return ((4.0/3) * Math.PI * Math.pow(radius,3));
    }

    @Override
    public String toString() {
        return "Solid shape centered at ("+super.getX()+","+super.getY()+","+super.getZ()+") with \n" +
                "faces : "+getNumberOfFaces()+" \n"+"vertices : "+getNumberOfVertices()+" \n"+"edges : "
                +getNumberOfEdges()+" \n"+"volume : "+getVolume()+'\n'
                +"radius: "+getRadius();
    }
}
