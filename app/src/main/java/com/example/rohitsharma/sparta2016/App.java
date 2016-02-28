package com.example.rohitsharma.sparta2016;

import android.app.Application;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by rohitsharma on 2016-02-28.
 */
public class App extends Application {

    //key is each food, value is quantity
    public static HashMap<UpcScannedObject, Integer> FOODS_EATEN_MAP;

    @Override
    public void onCreate() {
        FOODS_EATEN_MAP = new HashMap<>();
        super.onCreate();
    }

    //estimating values
    public static int CaloriesTotal(){
        int calories = 0;
        UpcScannedObject[] foods = (UpcScannedObject[])FOODS_EATEN_MAP.keySet().toArray();
        for(int i = 0; i < foods.length; i++){
            calories += foods[i].getCalories()*FOODS_EATEN_MAP.get(foods[i]);
        }
        return calories;
    }

    //TODO: FINISH COUNTER FOR EVERYTHING ELSE



    public static boolean IsPresentInMap (UpcScannedObject object) {
        if (FOODS_EATEN_MAP.size() == 0)
            return false;
        return FOODS_EATEN_MAP.containsKey(object);
    }

    public static void AddObjectToMap (UpcScannedObject object){
        FOODS_EATEN_MAP.put(object, 1);
    }

    public static void IncrementQuantity(UpcScannedObject object){
        FOODS_EATEN_MAP.put(object, FOODS_EATEN_MAP.get(object) + 1);
    }

    public static void ClearSingleFood(UpcScannedObject object){
        FOODS_EATEN_MAP.remove(object);
    }
}
