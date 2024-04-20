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
        int numberOfTitans;
        int i = 0;
        PriorityQueue<Titan> a = new PriorityQueue<>();
        if (laneTitans.size() > 5) {
            numberOfTitans = 5;
        } else {
            numberOfTitans = laneTitans.size();
        }
        while (i < numberOfTitans) {
            Titan target = laneTitans.peek();
            int damage = getDamage();
            resourcesGathered = resourcesGathered + target.takeDamage(damage);
            if (!target.isDefeated()) {
                a.add(laneTitans.poll());
            } else {
                laneTitans.remove();
            }
            i++;
        }
        laneTitans = a;
        return resourcesGathered;
    }
}
