package game.engine.titans;

public class ColossalTitan extends Titan {
    public static final int TITAN_CODE = 4;

    public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
            int resourcesValue, int dangerLevel) {
        super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);

    }

    @Override
    public boolean move() {
        int currentDistance = getDistance();
        int speed = getSpeed();
        if (currentDistance <= 0) {
            return true;
        }
        currentDistance = currentDistance - speed;
        if (hasReachedTarget() == true) {
            currentDistance = 0;
        } else {
            setDistance(currentDistance);
        }
        setSpeed(speed + 1);
        return hasReachedTarget();
    }
}
