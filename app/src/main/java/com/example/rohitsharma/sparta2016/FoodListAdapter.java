package com.example.rohitsharma.sparta2016;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rohitsharma on 2016-02-28.
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mTitle;
        private final TextView mQuantity;


        public ViewHolder(View itemView){
            super(itemView);
            //getting all the elements part of the card, aside from the image
            mTitle = (TextView)itemView.findViewById(R.id.name_of_food_list);
            mQuantity = (TextView)itemView.findViewById(R.id.quantity_food_list);

        }
    }

    private HashMap<UpcScannedObject, Integer> mFoodsMap;
    private ArrayList<UpcScannedObject> mFoodsList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UpcScannedObject object = mFoodsList.get(position);
        if (object != null){
            holder.mTitle.setText(object.getName());
            holder.mQuantity.setText(mFoodsMap.get(object)+"x");
        }

    }

    @Override
    public int getItemCount() {
        return mFoodsMap.size();
    }

    public FoodListAdapter(HashMap<UpcScannedObject, Integer> map, ArrayList<UpcScannedObject> foodsList) {
        mFoodsMap = map;
        mFoodsList = foodsList;
    }
}
