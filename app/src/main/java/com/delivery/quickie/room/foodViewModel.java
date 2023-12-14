package com.delivery.quickie.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.delivery.quickie.data.CartItems;
import com.delivery.quickie.data.food_items;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class foodViewModel extends AndroidViewModel {

    private final Repository repository;
    private final LiveData<List<food_items>> foodList;
    private final LiveData<List<CartItems>> cartItems;

    Executor executor;

    public foodViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        foodList = repository.getMeal();
        cartItems = repository.getCartItems();
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<food_items>> getFoodList(){
        return foodList;
    }

    // Cart Implementation
    public LiveData<List<CartItems>> getCartItems(){ return cartItems; }

    public void insertIntoCart(CartItems item){
        repository.insertIntoCart(item);
    }

    public void deleteFromCart(CartItems item){
        executor.execute(() -> repository.removeItemFromCart(item));
    }

    public LiveData<Integer> getTotalAmount(){
        return repository.getTotalAmount();
    }

    public  LiveData<Integer> getQuantity(String name){
        return repository.getQuantity(name);
    }

    public void remove(String name){
        executor.execute(() -> repository.remove(name));
    }

    public void deleteZeros(){
        executor.execute(repository::deleteZeroQuantity);
    }

    public CartItems findItem(String name){
        return repository.findCartItems(name);
    }

    public void add(String name){
        repository.add(name);
    }
}
