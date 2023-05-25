package com.delivery.quickie.room;

import java.util.List;

public class response {

    List<food_items> meals;

    public response(List<food_items> meals) {
        this.meals = meals;
    }

    public List<food_items> getMeals() {
        return meals;
    }

    public void setMeals(List<food_items> meals) {
        this.meals = meals;
    }
}
