package com.delivery.quickie.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface foodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<food_items> foodList);

    @Query("Select * from food")
    LiveData<List<food_items>> meals();

}
