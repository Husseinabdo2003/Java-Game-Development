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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    @Override
    public int getResourcesValue() {
        // TODO Auto-generated method stub
        return -1;
    }
    
}
