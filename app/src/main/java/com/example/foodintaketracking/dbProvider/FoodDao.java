package com.example.foodintaketracking.dbProvider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("select * from foods")
    LiveData<List<Food>> getAllFood();

    @Query("select * from foods where foodName=:name")
    List<Food> getFood(String name);

    @Insert
    void addFood(Food food);

    @Query("delete FROM foods")
    void deleteAllFood();
}
