package csd.uoc.gr.A21;

/**
 * @author csd4817
 */
public class Cylinder extends SolidShape {
    private double radius;
    private double height;

    Cylinder(double x,double y,double z,double radius,double height) {
        super(x,y,z);
        setRadius(radius);
        setHeight(height);
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    public double getRadius() {
        return radius;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Solid shape centered at ("+super.getX()+","+super.getY()+","+super.getZ()+") with \n" +
                "faces : "+getNumberOfFaces()+" \n"+"vertices : "+getNumberOfVertices()+" \n"+"edges : "
                +getNumberOfEdges()+" \n"+"volume : "+getVolume()+'\n'
                +"Radius: "+getRadius()+ "\n"+
                "Height: "+getHeight();
    }
}
