package game.engine.weapons;

import java.util.PriorityQueue;
import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {
    public static final int WEAPON_CODE = 1;

    public PiercingCannon(int baseDamage) {
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGathered = 0;
        int numberToAttack = 5;
        while (numberToAttack > 0 && !laneTitans.isEmpty()) {
            Titan target = laneTitans.peek();
            attack(target);
            int damage = getDamage();
            if (target.isDefeated()) {
                resourcesGathered = 2*damage;
                laneTitans.poll();
                numberToAttack--;
            }
        }
        return resourcesGathered;
    }
}
