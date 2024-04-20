package game.engine.lanes;

import game.engine.base.*;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Lane implements Comparable <Lane> {
    private final Wall laneWall;
    private int dangerLevel;
    private final PriorityQueue<Titan> titans;
    private final ArrayList<Weapon> weapons;

    public Lane (Wall laneWall){
        this.laneWall = laneWall;
        this.dangerLevel = 0;
        this.titans = new PriorityQueue<>();
        this.weapons = new ArrayList<>();
    }

    public Wall getLaneWall() {
        return laneWall;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public PriorityQueue<Titan> getTitans() {
        return titans;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void addTitan(Titan titan){
        titans.add(titan);
    }

    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }

    public void moveLaneTitans(){
        PriorityQueue <Titan> temp = new PriorityQueue<>();
        while(!titans.isEmpty()){
            Titan titan = titans.poll();
            if (!titan.hasReachedTarget()) {
                titan.move();
            }
            temp.add(titan);
        }
        titans.addAll(temp);
    }

    @Override
    public int compareTo(Lane o){
        return Integer.compare(this.dangerLevel, o.dangerLevel);
    }
}
