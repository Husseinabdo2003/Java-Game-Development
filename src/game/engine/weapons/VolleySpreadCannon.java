package game.engine.weapons;

import java.util.ArrayList;
import java.util.PriorityQueue;
import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon {
    public static final int WEAPON_CODE = 3;
    private final int minRange;
    private final int maxRange;

    public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
        super(baseDamage);
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        int resourcesGathered = 0;
        int damage = getDamage();
        ArrayList<Titan> defated = new ArrayList<>();
        for (Titan target : laneTitans) {
            int distance = target.getDistance();
            if (distance >= minRange && distance <= maxRange) {
                resourcesGathered = resourcesGathered + target.takeDamage(damage);
                if (target.isDefeated()) {
                    defated.add(target);
                }
            }
        }
        for (Titan defeatedTitan : defated) {
            laneTitans.remove(defeatedTitan);
        }
        return resourcesGathered;
    }
}