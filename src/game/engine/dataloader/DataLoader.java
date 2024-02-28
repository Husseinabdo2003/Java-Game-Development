package game.engine.dataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

public class DataLoader {
    private static String TITANS_FILE_NAME = "titans.csv";
    private String WEAPONS_FILE_NAME = "weapons.csv";

    public static HashMap <Integer,TitanRegistry> readTitanRegistry() throws IOException{
        HashMap <Integer,TitanRegistry> TitanHashMap = new HashMap<>();
        try(FileReader fr = new FileReader(TITANS_FILE_NAME); 
            BufferedReader br = new BufferedReader(fr);){
                String line;
                while((line = br.readLine()) != null){
                    String[] titaStrings = line.split(",");
                    int code = Integer.parseInt(titaStrings[0]);
                    int baseHealth = Integer.parseInt(titaStrings[1]);
                    int baseDamage = Integer.parseInt(titaStrings[2]);
                    int heightInMeters = Integer.parseInt(titaStrings[3]);
                    int speed = Integer.parseInt(titaStrings[4]);
                    int resourcesValue = Integer.parseInt(titaStrings[5]);
                    int dangerLevel = Integer.parseInt(titaStrings[6]);
                    TitanRegistry titanRegistry = new TitanRegistry(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
                    TitanHashMap.put(code, titanRegistry);
                }
            }
        return TitanHashMap;
    }

    public static HashMap <Integer,WeaponRegistry> readWeaponRegistry() throws IOException{
        HashMap <Integer, WeaponRegistry> WeaponHashMap = new HashMap<>();
        try(FileReader fr = new FileReader(TITANS_FILE_NAME); 
            BufferedReader br = new BufferedReader(fr);){
                String line;
                while((line = br.readLine()) != null){
                    String[] WeaponStrings = line.split(",");
                    int code = Integer.parseInt(WeaponStrings[0]);
                    int price = Integer.parseInt(WeaponStrings[1]);
                    int damage = Integer.parseInt(WeaponStrings[2]);
                    String name = WeaponStrings[3];
                    WeaponRegistry weaponRegistry;
                    if (WeaponStrings.length == 6) {
                        int minRange = Integer.parseInt(WeaponStrings[4]);
                        int maxRange = Integer.parseInt(WeaponStrings[5]);
                        weaponRegistry = new WeaponRegistry(code, price, damage, name, minRange, maxRange);
                    } else {
                        weaponRegistry = new WeaponRegistry(code, price, damage, name);
                    }
                }
            }
        return WeaponHashMap;
    }
}
