package com.delivery.quickie.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cuisine")
public class cuisine {

    @PrimaryKey(autoGenerate = true)
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String strArea;

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public cuisine(String strArea) {
        this.strArea = strArea;
    }
}
