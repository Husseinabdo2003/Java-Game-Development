package game.engine.weapons.factory;
import java.io.IOException;
import java.util.HashMap;
import game.engine.weapons.WeaponRegistry;

public class WeaponFactory {
    private HashMap<Integer, WeaponRegistry> weaponShop;
    
    public WeaponFactory() throws IOException{
        this.weaponShop = new HashMap<>();
    }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }
    
}
