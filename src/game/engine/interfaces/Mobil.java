package game.engine.interfaces;

public interface Mobil {
    public int getDistance();
    public void setDistance(int distance);
    public int getSpeed();
    public void setSpeed(int speed);

    default boolean hasReachedTarget(){
        return getDistance() <= 0;
    }

    default boolean move(){
        int currentDistance = getDistance();
        int speed = getSpeed();
        if(currentDistance <= 0){
            return true;
        }
        currentDistance = currentDistance - speed;
        if(currentDistance < 0){
            currentDistance = 0;
        }
        else{
            setDistance(currentDistance);
        }
        return currentDistance == 0;
    }
}
