package game.engine.interfaces;

public interface Attackee {
    public int  getCurrentHealth();
    public void setCurrentHealth(int health);
    public int getResourcesValue();

    default boolean isDefeated(){
        return getCurrentHealth() <= 0;
    }

    default int takeDamage(int damage){
        int currentHealth = getCurrentHealth();
        currentHealth = currentHealth - damage;
        setCurrentHealth(currentHealth);
        if(currentHealth <= 0){
            return getResourcesValue();
        }
        else{
            return 0;
        }
    }
}