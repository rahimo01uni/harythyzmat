package com.eccomerce.com;

import android.app.Application;

import com.eccomerce.com.main.bottom;

public class SingletonApp  {
private bottom _bottom;
    private static final SingletonApp ourInstance = new SingletonApp();

    public static SingletonApp getInstance() {
        return ourInstance;
    }

    private SingletonApp() {
    }
    public bottom getActivityInstance() {
        return _bottom;
    }
}
