package csd.uoc.gr.A23.person;

import csd.uoc.gr.A23.armor.Gun;

public abstract class GunCarrier {
    private Gun gun;
    GunCarrier(Gun gun) {
        setGun(gun);
    }
    public Gun getGun() {
        return this.gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }


}
