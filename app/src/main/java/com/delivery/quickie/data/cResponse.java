package com.delivery.quickie.data;

import java.util.List;

public class cResponse {

    List<cuisine> meals;

    public List<cuisine> getMeals() {
        return meals;
    }

    public void setMeals(List<cuisine> meals) {
        this.meals = meals;
    }

    public cResponse(List<cuisine> meals) {
        this.meals = meals;
    }
}
