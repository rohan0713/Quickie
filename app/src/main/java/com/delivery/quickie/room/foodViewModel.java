package com.delivery.quickie.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery.quickie.data.food_items;

import java.util.List;

public class foodViewModel extends AndroidViewModel {

    private final Repository repository;
    private final LiveData<List<food_items>> foodList;

    public foodViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodList = repository.getMeal();
    }

    public LiveData<List<food_items>> getFoodList(){
        return foodList;
    }
}
