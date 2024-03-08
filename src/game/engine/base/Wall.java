package game.engine.base;
import game.engine.interfaces.*;

public class Wall implements Attackee {
    private final int baseHealth;
    private int currentHealth;
    
    public Wall(int baseHealth){
        this.baseHealth = baseHealth;
        this.currentHealth = baseHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    @Override
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(currentHealth, 0);
    }

    @Override
    public int getResourcesValue() {
        return -1;
    }
}
