package com.delivery.quickie.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.delivery.quickie.data.CartItems;
import com.delivery.quickie.data.food_items;

import java.util.List;

@Dao
public interface foodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<food_items> foodList);

    @Query("Select * from food")
    LiveData<List<food_items>> meals();

    // Cart Implementation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFoodIntoCart(CartItems items);

    @Query("Update cart set quantity = quantity + 1 where foodName = :name")
    void add(String name);

    @Query("Select * from cart")
    LiveData<List<CartItems>> getCartItems();

    @Query("Select SUM(amount) from cart")
    LiveData<Integer> getTotalAmount();
    @Query("Select quantity from cart where foodName = :foodName")
    LiveData<Integer> getItemCount(String foodName);
    @Delete
    void removeFromCart(CartItems items);
    @Query("Update cart set quantity = quantity - 1 where foodName = :name AND quantity > 0")
    void remove(String name);
    @Query("Delete from cart where quantity = 0")
    void deleteZeroQuantity();
    @Query("Select * from cart where foodName = :foodName")
    LiveData<List<CartItems>> findItemInCart(String foodName);

}
