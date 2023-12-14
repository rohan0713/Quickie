package com.delivery.quickie.room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.delivery.quickie.data.CartItems;
import com.delivery.quickie.data.food_items;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {

    private final database foodDatabase;

    private final LiveData<List<food_items>> getFood;
    private final LiveData<List<CartItems>> getItemsFromCart;

    private final Executor executor;


    public Repository(Application application){
        foodDatabase = database.getInstance(application);
        getFood = foodDatabase.foodDao().meals();
        getItemsFromCart = foodDatabase.foodDao().getCartItems();
        executor = Executors.newSingleThreadExecutor();
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

    // Cart Functionality
    public LiveData<List<CartItems>> getCartItems(){
        return getItemsFromCart;
    }

    public void insertIntoCart(CartItems item){
        foodDao dao = foodDatabase.foodDao();
        executor.execute(() -> dao.insertFoodIntoCart(item));
    }

    public LiveData<Integer> getTotalAmount(){
        return foodDatabase.foodDao().getTotalAmount();
    }

    public  LiveData<Integer> getQuantity(String name){
        return foodDatabase.foodDao().getItemCount(name);
    }

    public void removeItemFromCart(CartItems item) {
        foodDatabase.foodDao().removeFromCart(item);
    }

    public void remove(String name){
        foodDatabase.foodDao().remove(name);
    }

    public void deleteZeroQuantity(){
        foodDatabase.foodDao().deleteZeroQuantity();
    }

    public CartItems findCartItems(String name){
        return foodDatabase.foodDao().findItemInCart(name);
    }

    public void add(String name){
        executor.execute(() -> foodDatabase.foodDao().add(name));
    }

}
