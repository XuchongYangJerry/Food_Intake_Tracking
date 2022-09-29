package com.example.foodintaketracking;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.foodintaketracking.databinding.RecyclerviewItemBinding;
import com.example.foodintaketracking.dbProvider.Food;

public class FoodListAdapter extends ListAdapter<Food, FoodViewHolder> {
    
    public FoodListAdapter(@NonNull DiffUtil.ItemCallback<Food> diffCallback){
        super(diffCallback);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding=
                RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FoodViewHolder(binding).onCreate(parent);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder viewHolder, int position){
        Food food = getItem(position);
        viewHolder.bind(food.getFoodImgFilePath(), food.getFoodName(), food.getFoodEatenPercent(), food.getFoodQty(), food.getFoodCategory(), food.getFoodEatenTime());
    }

    //identify if the two foods are the same
    public static class FoodDiff extends DiffUtil.ItemCallback<Food> {

        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem){
            return oldItem.getFoodName().equals(newItem.getFoodName());
        }
    }
}
