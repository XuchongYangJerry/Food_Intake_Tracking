package com.example.foodintaketracking.retrofit;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FoodNutrition {

    private List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName(){
        return items.get(0).getName();
    }

    public double getFat() {
        return items.get(0).getFat_total();
    }

    public double getSugar() {
        return items.get(0).getSugar();
    }

    public double getCalories() {
        return items.get(0).getCalories();
    }

    public int getCholesterol() {
        return items.get(0).getCholesterol();
    }

    public double getProtein() {
        return items.get(0).getProtein();
    }

    public double getCarbohydrate() {
        return items.get(0).getCarbohydrate();
    }
}

class Item {

    public double sugar_g;
    public String name;
    public double fat_total_g;
    public double calories;
    public int cholesterol_mg;
    public double protein_g;
    public double carbohydrates_total_g;

    public double getSugar() {
        return sugar_g;
    }

    public String getName() {
        return name;
    }

    public double getFat_total() {
        return fat_total_g;
    }

    public double getCalories() {
        return calories;
    }

    public int getCholesterol() {
        return cholesterol_mg;
    }

    public double getProtein() {
        return protein_g;
    }

    public double getCarbohydrate() {
        return carbohydrates_total_g;
    }
}

/**
class CHOLE{
    public String label;
    public int quantity;
    public String unit;

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}

class FAT{
    public String label;
    public double quantity;
    public String unit;

    public FAT(String label, double quantity, String unit) {
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}


class PROCNT{
    public String label;
    public double quantity;
    public String unit;

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}


public class FoodNutrition{
    public String uri;
    public int calories;
    public double totalWeight;
    public ArrayList<String> dietLabels;
    public ArrayList<String> healthLabels;
    public ArrayList<String> cautions;
    public TotalNutrients totalNutrients;

    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    public String getEnergy() {
        return totalNutrients.geteNERCKCAL().quantity + " " + totalNutrients.geteNERCKCAL().unit;
    }

    public String getFat() {
        return totalNutrients.getFat().quantity + " " + totalNutrients.getFat().unit;
    }

    public String getSuger() {
        return totalNutrients.getSugar().quantity + " " + totalNutrients.getSugar().unit;
    }

    public String getProtein() {
        return totalNutrients.getProtein().quantity + " " + totalNutrients.getProtein().unit;
    }

    public String getCholesterol() {
        return totalNutrients.getCholesterol().quantity + " " + totalNutrients.getCholesterol().unit;
    }
}

class ENERCKCAL{
    public String label;
    public double quantity;
    public String unit;

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}

class SUGAR{
    public String label;
    public double quantity;
    public String unit;

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}

class TotalNutrients {
    public ENERCKCAL eNERCKCAL;
    public FAT fAT;
    public SUGAR sUGAR;
    public PROCNT pROCNT;
    public CHOLE cHOLE;

    public TotalNutrients(ENERCKCAL eNERCKCAL, FAT fAT, SUGAR sUGAR, PROCNT pROCNT, CHOLE cHOLE) {
        this.eNERCKCAL = eNERCKCAL;
        this.fAT = fAT;
        this.sUGAR = sUGAR;
        this.pROCNT = pROCNT;
        this.cHOLE = cHOLE;
    }

    public ENERCKCAL geteNERCKCAL() {
        return eNERCKCAL;
    }

    public FAT getFat() {
        return fAT;
    }

    public SUGAR getSugar() {
        return sUGAR;
    }

    public PROCNT getProtein() {
        return pROCNT;
    }

    public CHOLE getCholesterol() {
        return cHOLE;
    }
}*/
