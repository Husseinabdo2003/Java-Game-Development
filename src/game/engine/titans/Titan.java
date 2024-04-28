package game.engine.titans;

import game.engine.interfaces.*;

public abstract class Titan implements Comparable<Titan>, Attackee, Attacker, Mobil {
    private final int baseHealth;
    private final int baseDamage;
    private final int heightInMeters;
    private int distanceFromBase;
    private int speed;
    private final int resourcesValue;
    private final int dangerLevel;
    private int currentHealth;

    public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
            int resourcesValue, int dangerLevel) {
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.heightInMeters = heightInMeters;
        this.distanceFromBase = distanceFromBase;
        this.speed = speed;
        this.resourcesValue = resourcesValue;
        this.dangerLevel = dangerLevel;
        this.currentHealth = baseHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = Math.max(health, 0);
    }

    public int getHeightInMeters() {
        return heightInMeters;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    @Override
    public int getDamage() {
        return baseDamage;
    }

    @Override
    public int getResourcesValue() {
        return resourcesValue;
    }

    @Override
    public int getDistance() {
        return distanceFromBase;
    }

    @Override
    public void setDistance(int distance) {
        this.distanceFromBase = Math.max(distance, 0);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int compareTo(Titan o) {
        return Integer.compare(this.distanceFromBase, o.distanceFromBase);
    }
}
