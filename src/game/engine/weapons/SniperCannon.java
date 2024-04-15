package game.engine.weapons;

import java.util.PriorityQueue;
import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
    public static final int WEAPON_CODE = 2;
    
    public SniperCannon(int baseDamage){
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'turnAttack'");
    }
}
