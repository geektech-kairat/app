package com.example.myapplication.share;

import android.content.Context;
import android.content.SharedPreferences;

public class Share {
    public static final String APP_PREFERENCES = "my settings";
    public static final String FOR_NAME = "name";
    public static final String SALARY = "salary";
    public static final String DEBT = "debt";
    private SharedPreferences sharedPreferences = null;

    public Share(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setForName(String name) {
        sharedPreferences.edit().putString(FOR_NAME, name).apply();
    }

    public String getForName() {
        return sharedPreferences.getString(FOR_NAME, "");
    }


    public void setForSalary(String name) {
        sharedPreferences.edit().putString(SALARY, name).apply();
    }


    public String getForDebt() {
        return sharedPreferences.getString(DEBT, "0".trim());
    }

    public void setForDebt(String name) {
        sharedPreferences.edit().putString(DEBT, name).apply();
    }

    public String getForSalary() {
        return sharedPreferences.getString(SALARY, "");
    }

}
