package game.engine.weapons;

import java.util.PriorityQueue;
import game.engine.titans.Titan;

public class WallTrap extends Weapon {
    public static final int WEAPON_CODE = 4;
    
    public WallTrap(int baseDamage){
        super(baseDamage);
    }

    @Override
    public int turnAttack(PriorityQueue<Titan> laneTitans) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'turnAttack'");
    }
}
