package game.engine.weapons;
import game.engine.interfaces.*;
public abstract class Weapon implements Attacker{
    private int baseDamage;
    public Weapon(int baseDamage){
        this.baseDamage = baseDamage;
    }
    public int getBaseDamage() {
        return baseDamage;
    }
    
    @Override
    public int getDamage() {
       return getBaseDamage();
    }

    
}
