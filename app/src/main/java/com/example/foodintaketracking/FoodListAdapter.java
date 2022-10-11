package com.example.foodintaketracking;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.foodintaketracking.databinding.RecyclerviewItemBinding;
import com.example.foodintaketracking.dbProvider.Food;
import com.example.foodintaketracking.dbProvider.FoodViewModel;

public class FoodListAdapter extends ListAdapter<Food, FoodViewHolder>{

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
        viewHolder.bind(food.getFoodImgFilePath(), food.getFoodName(), food.getFoodEatenPercent(), food.getFoodQty(), food.getFoodEatenTime());
        /**
        viewHolder.binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentList().remove(position);
                notifyDataSetChanged();
            }
        });*/

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
