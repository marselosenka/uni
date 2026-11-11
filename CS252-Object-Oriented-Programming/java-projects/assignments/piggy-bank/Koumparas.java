package csd.uoc.gr.A22;

public class Koumparas extends KoumparasADT {
    public int MAX_CAPACITY = 100;
    private int capacity;
    private double money;
    private double[] contains;
    private boolean status;

    @Override
    public void init() {

        setCapacity(0);
        setMoney(0);
        setStatus(true);
        this.contains = new double[100];
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public double getMoney() {
        return this.money;
    }

    @Override
    public boolean getStatus() {
        return this.status;
    }

    @Override
    public void breakKoumparas() throws KoumparasIsBrokenException {
        if(isBroken()) {
            throw new KoumparasIsBrokenException("Koumparas is already broken!");
        } else {
            setStatus(false);
        }
    }

    @Override
    public double[] getContains() throws KoumparasIsNotBrokenException {
        if(isBroken()) {
            return this.contains;
        } else {
            throw new KoumparasIsNotBrokenException("Koumparas is not opened yet!");
        }
    }

    @Override
    public boolean isFull() {
        if(capacity==MAX_CAPACITY) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isBroken() {
        if(getStatus()) {
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void addMoney(double money) throws KoumparasIsFullException, KoumparasIsBrokenException {
        if (!isBroken()) {
            if (!isFull()) {
                setMoney(getMoney() + money);
                contains[getCapacity()] = money;
                setCapacity(getCapacity() + 1);
            } else {
                throw new KoumparasIsFullException("You can't add money! Koumparas is full");
            }
        } else {
            throw new KoumparasIsBrokenException("Koumparas is broken! You cannot modify it");
        }
    }

}

