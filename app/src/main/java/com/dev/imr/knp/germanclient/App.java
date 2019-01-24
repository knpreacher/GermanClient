package com.dev.imr.knp.germanclient;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class App  extends Application{
    static RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }

    public static void addToQueue(Request request){
        queue.add(request);
    }
}
