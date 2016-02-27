package com.example.rohitsharma.sparta2016;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rohitsharma on 2016-02-27.
 */
public class Additive implements Serializable {
    public Additive(String forecastJsonStr) throws JSONException {
        JSONObject object = new JSONObject(forecastJsonStr);
        mCategoryName = categoryIntToName(object.getInt("category_id"));
        mAdditiveName = object.getString("name");
        mFunction = object.getString("function");
        mFoodsUsedIn = object.getString("foods");
        mHealthNotices = object.getString("notice");
        mAdditionalInfo = object.getString("info");
        mIsVegetarian = object.getString("veg").equals("1");
    }

    private String mCategoryName;
    private String mAdditiveName;
    private String mFunction;
    private String mFoodsUsedIn;
    private String mHealthNotices;
    private String mAdditionalInfo;
    private boolean mIsVegetarian;

    public String getAdditiveName() {
        return mAdditiveName;
    }

    public String getFunction() {
        return mFunction;
    }

    public String getFoodsUsedIn() {
        return mFoodsUsedIn;
    }

    public String getHealthNotices() {
        return mHealthNotices;
    }

    public String getAdditionalInfo() {
        return mAdditionalInfo;
    }

    public boolean isIsVegetarian() {
        return mIsVegetarian;
    }

    public String getCategoryName(){
        return mCategoryName;
    }

    private String categoryIntToName(int i) {
        String name;
        switch (i){
            case 1:
                name = "Colors";
                break;
            case 2:
                name = "Preservatives";
                break;
            case 3:
                name = "Antioxidants, Acidity Regulators";
                break;
            case 4:
                name = "Thickeners, Stabilizers, Emulsifiers";
                break;
            case 5:
                name = "Acidity Regulators, Anti-Caking Agents";
                break;
            case 6:
                name = "Flavour Enhancers";
                break;
            case 7:
                name = "Antibiotics";
                break;
            case 8:
                name = "Miscellaneous";
                break;
            default:
                name = "Other chemicals";
                break;
        }
        return name;
    }
}
