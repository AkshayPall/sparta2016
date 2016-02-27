package com.example.rohitsharma.sparta2016;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rohitsharma on 2016-02-27.
 */
public class Additive {
    public Additive(String forecastJsonStr) throws JSONException {
        JSONObject object = new JSONObject(forecastJsonStr);
        mCategoryName = categoryIntToName(object.getInt("category_id"));
    }

    private String mCategoryName;


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

    public String getCategoryName(){
        return mCategoryName;
    }
}
