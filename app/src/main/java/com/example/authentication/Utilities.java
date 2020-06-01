package com.example.authentication;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Utilities {
    public static boolean internetConnectionAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    return InetAddress.getByName("google.com");
                } catch (UnknownHostException e) {
                    return null;
                }
            });
            inetAddress = future.get(3000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (Exception e) {
            Log.d("Exception", "Exception: " + e.getMessage());
        }
        return inetAddress!=null && !inetAddress.equals("");
    }
}
