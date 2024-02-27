package game.engine.titans;
import game.engine.interfaces.*;

public abstract class Titan implements Comparable <Titan>, Attackee, Attacker, Mobil {
    private int baseHealth;
    private int baseDamage;
    private int heightInMeters;
    private int distanceFromBase;
    private int speed;
    private int resourcesValue;
    private int dangerLevel;
    private int currentHealth;
    
    public Titan(int baseHealth,int baseDamage, int heightInMeters, int distanceFromBase, int speed, int resourcesValue, int dangerLevel){
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.heightInMeters = heightInMeters;
        this.distanceFromBase = distanceFromBase;
        this.speed = speed;
        this.resourcesValue = resourcesValue;
        this.dangerLevel = dangerLevel;
        this.currentHealth= baseHealth;
    }
    
    public int getBaseHealth() {
        return baseHealth;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getHeightInMeters() {
        return heightInMeters;
    }

    public int getDistanceFromBase() {
        return distanceFromBase;
    }

    public void setDistanceFromBase(int distanceFromBase) {
        this.distanceFromBase = distanceFromBase;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getResourcesValue() {
        return resourcesValue;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    @Override
    public int getDamage() {
        
        return 0;
    }

    @Override
    public int getDistance() {
        
        return 0;
    }

    @Override
    public void setDistance(int distance) {
        
    }

    @Override
    public int compareTo(Titan o){
       return Integer.compare(this.distanceFromBase, o.distanceFromBase);
    }
}
