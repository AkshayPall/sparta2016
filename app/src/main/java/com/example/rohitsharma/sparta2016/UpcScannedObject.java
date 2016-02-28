package com.example.rohitsharma.sparta2016;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rohitsharma on 2016-02-27.
 */
public class UpcScannedObject implements Serializable {
    public UpcScannedObject (String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        mName = object.getString("brand_name")+" "+object.getString("item_name");
        mCalories = object.getInt("nf_calories");
        mFat = object.getInt("nf_total_fat");
        mContainsGluten = object.get("allergen_contains_gluten") != null;
        mSugar = object.getInt("nf_sugars");
        mProtein = object.getInt("nf_protein");
        mCarbohydrates = object.getInt("nf_total_carbohydrate");
    }

    //daily limits of nutrients
    static int DAILY_SUGAR_LIMIT_ = 125; //gender average, http://authoritynutrition.com/how-much-sugar-per-day/
    static int DAILY_CHOLESTEROL_LIMIT = 300; //mg per day, http://www.healthcentral.com/cholesterol/c/7291/131385/cholesterol/
    static int DAILY_CAL_LIMIT = 0;
    static int DAILY_FAT_LIMIT = DAILY_CAL_LIMIT/16; //25% of daily cal, where 1mg fat = 9cal, http://www.acaloriecounter.com/diet/how-much-fat-per-day/

    private String mName;
    private int mCalories;
    private int mFat;
    private boolean mContainsGluten;
    private int mSugar;
    private int mProtein;
    private int mCarbohydrates;

    public int getCarbs () {
        return mCarbohydrates;
    }

    public String getName() {
        return mName;
    }

    public int getCalories() {
        return mCalories;
    }

    public int getFat() {
        return mFat;
    }

    public boolean isContainsGluten() {
        return mContainsGluten;
    }

    public int getSugar() {
        return mSugar;
    }

    public int getProtein() {
        return mProtein;
    }
}
