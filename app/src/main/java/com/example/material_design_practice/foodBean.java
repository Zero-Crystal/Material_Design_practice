package com.example.material_design_practice;

public class foodBean {
    private String foodName;
    private int foodId;

    public foodBean(String foodName, int foodId) {
        this.foodName = foodName;
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }
}
