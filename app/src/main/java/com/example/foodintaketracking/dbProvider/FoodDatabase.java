package com.example.foodintaketracking.dbProvider;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class FoodDatabase extends RoomDatabase {

    public static final String CUSTOMER_DATABASE_NAME = "customer_database";

    public abstract FoodDao foodDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile FoodDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FoodDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FoodDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FoodDatabase.class, CUSTOMER_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
