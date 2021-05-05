package com.example.myapplication.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.ui.home.adapter.HomeModel;

import java.util.List;

@Dao
public interface FillDao {
    @Insert
    void insert(HomeModel homeModel);

    @Update
    void update(HomeModel homeModel);

    @Delete
    void delete(HomeModel homeModel);

    @Query("SELECT * FROM homemodel")
    LiveData<List<HomeModel>> getAll();



    @Query("SELECT * FROM homemodel ORDER by date ASC")
    List<HomeModel> getAllBySortDate();

    @Query("SELECT * FROM homemodel ORDER by debt DESC")
    List<HomeModel> getAllBySortDebt();

    @Query("SELECT * FROM homemodel ORDER by editDate ASC")
    List<HomeModel> getAllBySortDateTime();



    @Query("DELETE FROM homemodel")
    void deleteAll();







}
