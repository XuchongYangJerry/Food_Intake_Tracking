package com.example.foodintaketracking.dbProvider;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.File;
import java.util.Date;


@Entity(tableName = "foods")
public class Food {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "foodId")
    private int id;

    @ColumnInfo(name = "foodName")
    private String foodName;

    @ColumnInfo(name = "foodImgFilePath")
    private String foodImgFilePath;

    @ColumnInfo(name = "foodEatenAt")
    private Date foodEatenAt;

    @ColumnInfo(name = "foodEatenPercent")
    private Double foodEatenPercent;

    @ColumnInfo(name = "foodDesc")
    private String foodDesc;

    @ColumnInfo(name = "foodCategory")
    private String foodCategory;

    @ColumnInfo(name = "foodQty")
    private int foodQty;

    @ColumnInfo(name = "foodQtyMetric")
    private String foodQtyMetric;

    @ColumnInfo(name = "foodEatenTime")
    private String foodEatenTime;

    @ColumnInfo(name = "foodDuration")
    private int foodDuration;

    public Food(String foodName, String foodImgFilePath, Date foodEatenAt, Double foodEatenPercent, String foodDesc, String foodCategory, int foodQty, String foodQtyMetric, String foodEatenTime, int foodDuration) {
        this.foodName = foodName;
        this.foodImgFilePath = foodImgFilePath;
        this.foodEatenAt = foodEatenAt;
        this.foodEatenPercent = foodEatenPercent;
        this.foodDesc = foodDesc;
        this.foodCategory = foodCategory;
        this.foodQty = foodQty;
        this.foodQtyMetric = foodQtyMetric;
        this.foodEatenTime = foodEatenTime;
        this.foodDuration = foodDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImgFilePath() {
        return foodImgFilePath;
    }

    public void setFoodImgFilePath(String foodImgFilePath) {
        this.foodImgFilePath = foodImgFilePath;
    }

    public Date getFoodEatenAt() {
        return foodEatenAt;
    }

    public void setFoodEatenAt(Date foodEatenAt) {
        this.foodEatenAt = foodEatenAt;
    }

    public Double getFoodEatenPercent() {
        return foodEatenPercent;
    }

    public void setFoodEatenPercent(Double foodEatenPercent) {
        this.foodEatenPercent = foodEatenPercent;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public int getFoodQty() {
        return foodQty;
    }

    public void setFoodQty(int foodQty) {
        this.foodQty = foodQty;
    }

    public String getFoodQtyMetric() {
        return foodQtyMetric;
    }

    public void setFoodQtyMetric(String foodQtyMetric) {
        this.foodQtyMetric = foodQtyMetric;
    }

    public String getFoodEatenTime() {
        return foodEatenTime;
    }

    public void setFoodEatenTime(String foodEatenTime) {
        this.foodEatenTime = foodEatenTime;
    }

    public int getFoodDuration() {
        return foodDuration;
    }

    public void setFoodDuration(int foodDuration) {
        this.foodDuration = foodDuration;
    }
}