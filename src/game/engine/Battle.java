package game.engine;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.Weapon;
import game.engine.weapons.factory.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Battle {
    private static final int[][] PHASES_APPROACHING_TITANS = {
            { 1, 1, 1, 2, 1, 3, 4 },
            { 2, 2, 2, 1, 3, 3, 4 },
            { 4, 4, 4, 4, 4, 4, 4 }
    };
    private static final int WALL_BASE_HEALTH = 10000;
    private int numberOfTurns;
    private int resourcesGathered;
    private BattlePhase battlePhase;
    private int numberOfTitansPerTurn;
    private int score;
    private int titanSpawnDistance;
    private final WeaponFactory weaponFactory;
    private final HashMap<Integer, TitanRegistry> titansArchives;
    private final ArrayList<Titan> approachingTitans;
    private final PriorityQueue<Lane> lanes;
    private final ArrayList<Lane> originalLanes;

    public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
            int initialResourcesPerLane) throws IOException {
        this.numberOfTurns = numberOfTurns;
        this.resourcesGathered = initialNumOfLanes * initialResourcesPerLane;
        this.battlePhase = BattlePhase.EARLY;
        this.numberOfTitansPerTurn = 1;
        this.score = score;
        this.titanSpawnDistance = titanSpawnDistance;
        this.weaponFactory = new WeaponFactory();
        this.titansArchives = DataLoader.readTitanRegistry();
        this.approachingTitans = new ArrayList<>();
        this.lanes = new PriorityQueue<>();
        this.originalLanes = new ArrayList<>();
        initializeLanes(initialNumOfLanes);
    }

    public static int[][] getPHASES_APPROACHING_TITANS() {
        return PHASES_APPROACHING_TITANS;
    }

    public static int getWallBaseHealth() {
        return WALL_BASE_HEALTH;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getResourcesGathered() {
        return resourcesGathered;
    }

    public void setResourcesGathered(int resourcesGathered) {
        this.resourcesGathered = resourcesGathered;
    }

    public BattlePhase getBattlePhase() {
        return battlePhase;
    }

    public void setBattlePhase(BattlePhase battlePhase) {
        this.battlePhase = battlePhase;
    }

    public int getNumberOfTitansPerTurn() {
        return numberOfTitansPerTurn;
    }

    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
        this.numberOfTitansPerTurn = numberOfTitansPerTurn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTitanSpawnDistance() {
        return titanSpawnDistance;
    }

    public void setTitanSpawnDistance(int titanSpawnDistance) {
        this.titanSpawnDistance = titanSpawnDistance;
    }

    public WeaponFactory getWeaponFactory() {
        return weaponFactory;
    }

    public HashMap<Integer, TitanRegistry> getTitansArchives() {
        return titansArchives;
    }

    public ArrayList<Titan> getApproachingTitans() {
        return approachingTitans;
    }

    public PriorityQueue<Lane> getLanes() {
        return lanes;
    }

    public ArrayList<Lane> getOriginalLanes() {
        return originalLanes;
    }

    private void initializeLanes(int numOfLanes) {
        for (int i = 0; i < numOfLanes; i++) {
            Wall wall = new Wall(getWallBaseHealth());
            Lane lane = new Lane(wall);
            getOriginalLanes().add(lane);
            getLanes().add(lane);
        }
    }

    public void refillApproachingTitans() {
        approachingTitans.clear();
        int[] phaseTitan = getPHASES_APPROACHING_TITANS()[battlePhase.ordinal()];
        for (int i = 0; i < phaseTitan.length; i++) {
            TitanRegistry titanRegistry = getTitansArchives().get(phaseTitan[i]);
            if (titanRegistry != null) {
                Titan titan = titanRegistry.spawnTitan(titanSpawnDistance);
                approachingTitans.add(titan);
            }
        }
    }

    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException,
            InvalidLaneException {
        if (lane.isLaneLost() || !lanes.contains(lane)) {
            throw new InvalidLaneException();
        } else {
            FactoryResponse factoryResponse = getWeaponFactory().buyWeapon(resourcesGathered, weaponCode);
            Weapon weapon = factoryResponse.getWeapon();
            resourcesGathered = factoryResponse.getRemainingResources();
            lane.addWeapon(weapon);
            performTurn();
        }
    }

    public void passTurn() {
        performTurn();
    }

    private void moveTitans() {
        for (Lane lane : lanes) {
            lane.moveLaneTitans();
        }
    }

    private void addTurnTitansToLane() {
        if (approachingTitans.isEmpty()) {
            refillApproachingTitans();
        }

        Lane leastDangerousLane = lanes.peek();
        for (int i = 0; i < numberOfTitansPerTurn && !approachingTitans.isEmpty(); i++) {
            Titan titan = getApproachingTitans().get(0);
            approachingTitans.remove(0);
            leastDangerousLane.addTitan(titan);
            lanes.remove(leastDangerousLane);
            lanes.add(leastDangerousLane);
            if (approachingTitans.isEmpty()) {
                refillApproachingTitans();
            }
        }
    }

    private int performWeaponsAttacks() {
        int totalResources = 0;
        for (Lane lane : lanes) {
            totalResources += lane.performLaneWeaponsAttacks();
        }
        setScore(getScore() + totalResources);
        resourcesGathered += totalResources;
        setResourcesGathered(resourcesGathered);
        return totalResources;
    }

    private int performTitansAttacks() {
        int totalResources = 0;
        PriorityQueue<Lane> Lanes = new PriorityQueue<>();
        PriorityQueue<Lane> LanesToRemove = new PriorityQueue<>();
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                int resouces = lane.performLaneTitansAttacks();
                if (lane.isLaneLost()) {
                    LanesToRemove.add(lane);
                }
                totalResources += resouces;
            }
        }
        lanes.removeAll(LanesToRemove);
        lanes.addAll(Lanes);
        return totalResources;
    }

    private void updateLanesDangerLevels() {
        PriorityQueue<Lane> tempLanes = new PriorityQueue<>();
        for (Lane lane : lanes) {
            lane.setDangerLevel(0);
            int dangerLevel = 0;
            for (Titan titan : lane.getTitans()) {
                dangerLevel += titan.getDangerLevel();
            }
            lane.setDangerLevel(dangerLevel);
            tempLanes.add(lane);
        }
        lanes.clear();
        lanes.addAll(tempLanes);
    }

    private void finalizeTurns() {
        setNumberOfTurns(getNumberOfTurns() + 1);
        if (numberOfTurns < 15) {
            setBattlePhase(BattlePhase.EARLY);
        } else if (numberOfTurns < 30) {
            setBattlePhase(BattlePhase.INTENSE);
        } else {
            setBattlePhase(BattlePhase.GRUMBLING);
            if (numberOfTurns > 30 && numberOfTurns % 5 == 0) {
                setNumberOfTitansPerTurn(getNumberOfTitansPerTurn() * 2);
            }
        }
    }

    private void performTurn() {
        if (!isGameOver()) {
            moveTitans();

            getBattlePhase();

            performWeaponsAttacks();

            performTitansAttacks();

            addTurnTitansToLane();

            updateLanesDangerLevels();

            finalizeTurns();
        }
    }

    public boolean isGameOver() {
        for (Lane lane : lanes) {
            if (!lane.isLaneLost()) {
                return false;
            }
        }
        return true;
    }
}