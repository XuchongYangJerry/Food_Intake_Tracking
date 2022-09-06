package com.example.foodintaketracking.dbProvider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodRepository {

    private FoodDao mFoodDao;
    private LiveData<List<Food>> mAllFood;

    FoodRepository(Application application) {
        FoodDatabase db = FoodDatabase.getDatabase(application);
        mFoodDao = db.foodDao();
        mAllFood = mFoodDao.getAllFood();
    }
    LiveData<List<Food>> getAllFood() {
        return mAllFood;
    }
    void insert(Food food) {
        FoodDatabase.databaseWriteExecutor.execute(() -> mFoodDao.addFood(food));
    }

    void deleteAll(){
        FoodDatabase.databaseWriteExecutor.execute(()->{
            mFoodDao.deleteAllFood();
        });
    }
}
