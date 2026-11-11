package csd.uoc.gr.A22;

/**
 *
 * @author csd4817
 */
abstract class KoumparasADT {

    /**
     * Constructor, initializes critical data.
     */
    KoumparasADT() {
        init();
    }

    /**
     * @pre  Must not be initialized before, all values must be undefined.
     * @post  Initializes some private variables and is never called again.
     */
    abstract public void init();

    /**
     *  @pre  Capacity must be initialized before by init() and >=0, before executing for first time.
     *  @post capacity must be increased(Most likely +1)
     * @param capacity increases the current items on the koumparas.
     */
    abstract public void setCapacity(int capacity);

    /**
     * @pre money >=0
     * @post Must set the money as much as it was given.
     * @param money updates the total value of the koumparas.
     */
    abstract public void setMoney(double money);

    /**
     *
     *  @post Must update the status.
     * @param status status of koumparas(broken or normal)
     */
    abstract public void setStatus(boolean status);

    /**
     *  @pre capacity >= 0
     *  @post Must return capacity, no other changes on its value.
     * @return current capacity
     */
    abstract public int getCapacity();
    /**
     *  @pre money >=0
     *  @post Must return money, no other changes on its value.
     * @return current money
     */
    abstract public double getMoney();

    /**
     *
     * @post Must return status boolean, no changes on its value.
     * @return status of Koumparas.
     */
    abstract public boolean getStatus();

    /**
     * @pre Koumparas != broken
     * @post Updates status of Koumparas' to false.
     * @throws KoumparasIsBrokenException if we attempt to re-break Koumparas.
     */
    abstract public void breakKoumparas() throws KoumparasIsBrokenException;

    /**
     *  Checks the contains of Koumparas.
     *  @pre contains != NULL
     *  @post Should return an array of all values inserted to Koumparas.
     * @throws KoumparasIsNotBrokenException if we attempt to view contains without Koumparas being broken yet.
     * @return The contains of Koumparas(must be broken first)
     */
    abstract public double[] getContains() throws KoumparasIsNotBrokenException;

    /**
     *  Checks if Koumparas is full
     *  @pre must not be called before initialization, should return true as long as capacity <= MAX_CAPACITY
     *  @post must not update any value, just give a boolean.
     * @return True or false, depending on the capacity.
     */
    abstract public boolean isFull();

    /**
     *  Checks if it is broken
     *  @pre Koumparas must not be broken before
     *  @post must not change status value.
     * @return True or false, checks whether Koumparas is broken.
     */
    abstract public boolean isBroken();

    /**
     * Adds money to the Koumparas.
     * @pre Koumparas must be initialized
     * @post adds money to Koumparas
     * @param money
     * @throws KoumparasIsFullException if attempts to add on a full Koumparas.
     * @throws KoumparasIsBrokenException if Koumparas is broken and we attempt to add money.
     */
    abstract public void addMoney(double money) throws KoumparasIsFullException, KoumparasIsBrokenException;

}
