package com.example.rohitsharma.sparta2016;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rohitsharma on 2016-02-27.
 */
public class UpcScannedObject {
    public UpcScannedObject (String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        mName = object.getString("brand_name")+object.getString("item_name");
        mCalories = object.getInt("nf_calories");
        mFat = object.getInt("nf_total_fat");
        mContainsGluten = object.get("allergen_contains_gluten") != null;
        mSugar = object.getInt("nf_sugars");
        mProtein = object.getInt("nf_protein");
    }

    private String mName;
    private int mCalories;
    private int mFat;
    private boolean mContainsGluten;
    private int mSugar;
    private int mProtein;
}
