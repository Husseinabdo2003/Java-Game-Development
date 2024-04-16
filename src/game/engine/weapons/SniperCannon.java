package game.engine.weapons;

import java.util.PriorityQueue;
import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
    public static final int WEAPON_CODE = 2;

    public SniperCannon(int baseDamage) {
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGathered = 0;
        int damage = getDamage();
        while (!laneTitans.isEmpty()) {
            Titan target = laneTitans.peek();
            attack(target);
            resourcesGathered = resourcesGathered + damage;
            if (target.isDefeated()) {
                laneTitans.poll();
            }
        }
        return resourcesGathered;
    }
}
