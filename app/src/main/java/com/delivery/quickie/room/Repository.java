package com.delivery.quickie.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.delivery.quickie.data.food_items;

import java.util.List;

public class Repository {

    private final database foodDatabase;

    private final LiveData<List<food_items>> getFood;

    public Repository(Application application){

        foodDatabase = database.getInstance(application);
        getFood = foodDatabase.foodDao().meals();

    }

    public LiveData<List<food_items>> getMeal(){

        return getFood;
    }

    public void insert(List<food_items> foodList){
            new InsertAsyncTask(foodDatabase).execute(foodList);
    }

    static class InsertAsyncTask extends AsyncTask<List<food_items>, Void, Void>{

        private final foodDao foodDao;

        InsertAsyncTask(database foodDatabase){
            foodDao = foodDatabase.foodDao();
        }

        @Override
        protected Void doInBackground(List<food_items>... lists) {
            foodDao.insert(lists[0]);
            return null;
        }
    }

}
