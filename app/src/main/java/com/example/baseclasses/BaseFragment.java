package com.example.baseclasses;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.example.authentication.Utilities.internetConnectionAvailable;

public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!internetConnectionAvailable()) {
            try {
                AlertDialog.Builder builder =new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("No internet Connection");
                builder.setMessage("Please turn on internet connection to continue");
                builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().onBackPressed();
                        // System.exit(0);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } catch (Exception e) {
                Log.d("Exception", "Exception: " + e.getMessage());
            }
        }
    }

    protected void showDialog(String title, String msg) {
        AlertDialog.Builder builder =new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("close", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
