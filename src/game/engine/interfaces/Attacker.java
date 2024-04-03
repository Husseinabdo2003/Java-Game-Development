package game.engine.interfaces;

public interface Attacker {
    public int getDamage();

    default int attack(Attackee target){
        int damage = getDamage();
        int resourcesGathered = target.takeDamage(damage);
        return resourcesGathered;
    }
}
