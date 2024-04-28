package game.engine.weapons;

import java.util.PriorityQueue;
import game.engine.interfaces.*;
import game.engine.titans.Titan;

public abstract class Weapon implements Attacker {
    private final int baseDamage;

    public Weapon(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public abstract int turnAttack(PriorityQueue<Titan> laneTitans);

    @Override
    public int getDamage() {
        return baseDamage;
    }

}
