package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;

public class WeaponFactory {
    private final HashMap<Integer, WeaponRegistry> weaponShop;

    public WeaponFactory() throws IOException {
        this.weaponShop = DataLoader.readWeaponRegistry();
    }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }

    public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException {
        if (weaponShop.containsKey(weaponCode) == false) {
            throw new IllegalArgumentException("Invalid weapon code");
        }
        WeaponRegistry weaponRegistry = weaponShop.get(weaponCode);
        if (resources < weaponRegistry.getPrice()) {
            throw new InsufficientResourcesException(resources);
        }
        Weapon weapon = weaponRegistry.buildWeapon();
        int remainingResources = resources - weaponRegistry.getPrice();
        return new FactoryResponse(weapon, remainingResources);
    }

    public void addWeaponToShop(int code, int price) {
        WeaponRegistry weaponRegistry = new WeaponRegistry(code, price);
        weaponShop.put(code, weaponRegistry);
    }

    public void addWeaponToShop(int code, int price, int damage, String name) {
        WeaponRegistry weaponRegistry = new WeaponRegistry(code, price, damage, name);
        weaponShop.put(code, weaponRegistry);
    }

    public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange) {
        WeaponRegistry weaponRegistry = new WeaponRegistry(code, price, damage, name, minRange, maxRange);
        weaponShop.put(code, weaponRegistry);
    }

}
