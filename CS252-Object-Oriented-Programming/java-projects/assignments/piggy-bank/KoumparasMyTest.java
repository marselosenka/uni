package csd.uoc.gr.A22;

import java.util.Arrays;

public class KoumparasMyTest {

    public static void main(String[] args) throws KoumparasIsBrokenException, KoumparasIsFullException, KoumparasIsNotBrokenException {
        Koumparas K = new Koumparas();
        System.out.println(K.getCapacity());

        K.addMoney(5.12);
        K.addMoney(7.25);
        K.addMoney(5);
        System.out.println(K.getCapacity());
        System.out.println(Arrays.toString(K.getContains()));
        K.breakKoumparas();
    }
}
