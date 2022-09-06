package com.example.foodintaketracking.dbProvider;

import static java.util.Objects.*;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository mRepository;
    private LiveData<List<Food>> mAllFood;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FoodRepository(application);
        mAllFood = mRepository.getAllFood();
    }

    public LiveData<List<Food>> getAllFood() {
        return mAllFood;
    }

    public void insert(Food food) {
        mRepository.insert(food);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }
}
