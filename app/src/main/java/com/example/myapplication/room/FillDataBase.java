package com.example.myapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.ui.home.adapter.HomeModel;

@Database(entities = {HomeModel.class}, version = 1)
public abstract class FillDataBase extends RoomDatabase {
    public abstract FillDao fillDao();
}
