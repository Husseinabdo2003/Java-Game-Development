package game.engine.weapons;
import game.engine.interfaces.*;
public abstract class Weapon implements Attacker{
    private final int baseDamage;
    
    public Weapon(int baseDamage){
        this.baseDamage = baseDamage;
    }
    
    @Override
    public int getDamage() {
       return baseDamage;
    }    
}
