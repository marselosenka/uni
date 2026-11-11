package csd.uoc.gr.A23.person;

import csd.uoc.gr.A23.armor.Gun;
import csd.uoc.gr.A23.exception.DeadCharacterException;
import csd.uoc.gr.A23.utilities.Utilities;

public abstract class Member extends GunCarrier implements Character  {
    private int health;
    private String name;
    private boolean bodyArmor;
    private int iq;
    private int physicalStrength;
    Member(String name,int health,Gun gun,boolean bodyArmor,int iq,int physicalStrength) {
        super(gun);
        this.health = health;
        this.name = name;
        this.bodyArmor = bodyArmor;
        this.iq = iq;
        this.physicalStrength = physicalStrength;

    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        if (getHealth()>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBodyArmor() {
        if(this.bodyArmor) {
            return true;
        } else {
            return false;
        }
    }

    public void damageHealth(int damage) {
        this.health = getHealth() - damage;
    }

    public int calculateAtomicPower() {
        return this.iq + this.physicalStrength;
    }

    public int handleHostage() {
        return Utilities.getRandomVal(-2,2);
    }
    public void fight(Character adversary) throws DeadCharacterException {
        if(!this.isAlive()) {
            throw new DeadCharacterException("Character is dead!");
        }
        int damage = 0;
        if(!(adversary instanceof Member)) {
            throw new IllegalArgumentException("Adversary is not Member!");
        } else if(adversary instanceof Insider){
            damage = 2;
            adversary.damageHealth(damage);
        } else {
            damage = this.getGun().getPower() + this.physicalStrength + handleHostage();
            if(((Member) adversary).hasBodyArmor()) {
                adversary.damageHealth(damage/2);
            } else {
                adversary.damageHealth(damage);
            }
        }

    }
}

