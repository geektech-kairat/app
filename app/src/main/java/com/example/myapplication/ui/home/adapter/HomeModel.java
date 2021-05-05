package com.example.myapplication.ui.home.adapter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HomeModel implements Comparable<HomeModel> {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private String description;
    private String debt;
    private String date;
    private String editDate;
    private long editDate2;

    public HomeModel(String name, String description, String debt, String date, String editDate, long editDate2) {
        this.name = name;
        this.description = description;
        this.debt = debt;
        this.date = date;
        this.editDate = editDate;
        this.editDate2 = editDate2;
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

    public long getEditDate2() {
        return editDate2;
    }

    public void setEditDate2(long editDate2) {
        this.editDate2 = editDate2;
    }

    @Override
    public int compareTo(HomeModel o) {
        long dateOther = o.id;
        return (int) (this.id -dateOther);
    }
}
