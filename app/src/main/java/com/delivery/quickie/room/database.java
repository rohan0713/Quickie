package com.delivery.quickie.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.delivery.quickie.data.food_items;

@Database(entities = {food_items.class}, version = 2)
public abstract class database extends RoomDatabase {

    public abstract foodDao foodDao();

    public static final String Database_name = "Database";

    private static volatile database instance;

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance);
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {

        private final foodDao foodDao;

        PopulateAsyncTask(database foodDatabase) {

            foodDao = foodDatabase.foodDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    public static database getInstance(Context context) {

        synchronized (database.class) {
            if (instance == null) {

                instance = Room.databaseBuilder(context, database.class, Database_name)
                        .addCallback(callback)
                        .fallbackToDestructiveMigration()
                        .build();
            }

        }
        return instance;
    }

}
