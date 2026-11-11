package csd.uoc.gr.A23.armor;

public abstract class Gun {
    private int power;
    Gun(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
