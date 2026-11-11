package csd.uoc.gr.A21;

/**
 * @author csd4817
 */
public class Cube extends SolidShape{
    private double side;
    Cube(double x,double y,double z,double side) {
        super(x,y,z);
        setSide(side);
        super.setNumberOfFaces(6);
        super.setNumberOfEdges(12);
        super.setNumberOfVertices(8);
        super.setVolume(Math.pow(side,3));
    }

    public double getSide() {
        return side;
    }
    public void setSide(double side) {
        this.side = side;
    }
    @Override
    public String toString() {
        return "Solid shape centered at ("+super.getX()+","+super.getY()+","+super.getZ()+") with \n" +
                "faces : "+getNumberOfFaces()+" \n"+"vertices : "+getNumberOfVertices()+" \n"+"edges : "
                +getNumberOfEdges()+" \n"+"volume : "+getVolume()+'\n'
                +"side: "+getSide();
    }

}
