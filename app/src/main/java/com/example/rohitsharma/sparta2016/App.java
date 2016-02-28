package com.example.rohitsharma.sparta2016;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Created by rohitsharma on 2016-02-28.
 */
public class App extends Application {

    //key is each food, value is quantity
    public static HashMap<UpcScannedObject, Integer> FOODS_EATEN_MAP;
    public static ArrayList<UpcScannedObject> FOOD_LIST = new ArrayList<>();

    @Override
    public void onCreate() {
        FOODS_EATEN_MAP = new HashMap<>();
        super.onCreate();
    }

    public static ArrayList<UpcScannedObject> GetFoodList() {
        return FOOD_LIST;
    }

    //estimating values
    public static int CaloriesTotal(){
        int calories = 0;

        Object[] foods = FOODS_EATEN_MAP.keySet().toArray();
        UpcScannedObject[] realFoods = new UpcScannedObject[foods.length];
        int spook = 0;
        for (Object food : foods) {
            realFoods[spook] = (UpcScannedObject)food;
            spook++;
        }

        for(int i = 0; i < foods.length; i++){
            calories += realFoods[i].getCalories()*FOODS_EATEN_MAP.get(foods[i]);
        }
        return calories;
    }

    //estimating values
    public static int FatTotal(){
        int fat = 0;
        Object[] foods = FOODS_EATEN_MAP.keySet().toArray();
        UpcScannedObject[] realFoods = new UpcScannedObject[foods.length];
        int spook = 0;
        for (Object food : foods) {
            realFoods[spook] = (UpcScannedObject)food;
            spook++;
        }
        for(int i = 0; i < foods.length; i++){
            fat += realFoods[i].getFat()*FOODS_EATEN_MAP.get(foods[i]);
        }
        return fat;
    }

    //estimating values
    public static int CarbsTotal(){
        int carbs = 0;
        Object[] foods = FOODS_EATEN_MAP.keySet().toArray();
        UpcScannedObject[] realFoods = new UpcScannedObject[foods.length];
        int spook = 0;
        for (Object food : foods) {
            realFoods[spook] = (UpcScannedObject)food;
            spook++;
        }
        for(int i = 0; i < foods.length; i++){
            carbs += realFoods[i].getCarbs()*FOODS_EATEN_MAP.get(foods[i]);
        }
        return carbs;
    }

    //estimating values
    public static int ProteinTotal(){
        int protein = 0;
        Object[] foods = FOODS_EATEN_MAP.keySet().toArray();
        UpcScannedObject[] realFoods = new UpcScannedObject[foods.length];
        int spook = 0;
        for (Object food : foods) {
            realFoods[spook] = (UpcScannedObject)food;
            spook++;
        }
        for(int i = 0; i < foods.length; i++){
            protein += realFoods[i].getCalories()*FOODS_EATEN_MAP.get(foods[i]);
        }
        return protein;
    }

    //estimating values
    public static int SugarTotal(){
        int sugar = 0;
        Object[] foods = FOODS_EATEN_MAP.keySet().toArray();
        UpcScannedObject[] realFoods = new UpcScannedObject[foods.length];
        int spook = 0;
        for (Object food : foods) {
            realFoods[spook] = (UpcScannedObject)food;
            spook++;
        }
        for(int i = 0; i < foods.length; i++){
            sugar += realFoods[i].getSugar()*FOODS_EATEN_MAP.get(foods[i]);
        }
        return sugar;
    }

    public static int ExcessCalories(int calories, int fat, int weight, int height, boolean isFemale, int age) {
        int excess = 0;
        double BMR = isFemale ? 10*weight+6.25*height-5*age-161 : 10*weight+6.25*height-5*age+5;
        Log.wtf("BMR", ""+BMR);

        //Total energy expenditure allowed, assume hackers have no exercise
        Double TEE = BMR*1.2;
        Log.wtf("TEE", ""+TEE);

        excess = calories+fat*9 - TEE.intValue();
        Log.wtf("EXCESS", ""+excess);

        if (excess > 0){
            return excess;
        } else {
            return 0;
        }
    }

    public static int WeeksToWait (int calories, int fat, int weight, int height, boolean isFemale, int age, int weeklyCaloriesBurnt) {
        return  ExcessCalories(calories, fat, weight, height, isFemale, age)/weeklyCaloriesBurnt;
    }



    public static boolean IsPresentInMap (UpcScannedObject object) {
        if (FOODS_EATEN_MAP.size() == 0)
            return false;
        return FOODS_EATEN_MAP.containsKey(object);
    }

    public static void AddObjectToMap (UpcScannedObject object){
        FOOD_LIST.add(object);
        FOODS_EATEN_MAP.put(object, 1);
    }

    public static void IncrementQuantity(UpcScannedObject object){
        FOODS_EATEN_MAP.put(object, FOODS_EATEN_MAP.get(object) + 1);
    }

    public static void ClearSingleFood(UpcScannedObject object){
        FOODS_EATEN_MAP.remove(object);
    }
}
