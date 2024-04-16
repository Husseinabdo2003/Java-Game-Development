package game.engine.weapons;

import java.util.PriorityQueue;
import game.engine.titans.Titan;

public class WallTrap extends Weapon {
    public static final int WEAPON_CODE = 4;

    public WallTrap(int baseDamage) {
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGathered = 0;
        int damage = getDamage();
        while (!laneTitans.isEmpty() && laneTitans.peek().hasReachedTarget()) {
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
