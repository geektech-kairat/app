package com.example.myapplication.ui.home.adapter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HomeModel {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String description;
    private String debt;
    private String date;
    private String editDate = "Не был изменен";

    public HomeModel(String name, String description, String debt, String date) {
        this.name = name;
        this.description = description;
        this.debt = debt;
        this.date = date;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
