package com.example.baseclasses;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.monet_transfer.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void replaceFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (isAddToBackStack) {
            fragmentTransaction.replace(R.id.root_view, baseFragment, fragment_id);
            fragmentTransaction.addToBackStack(fragment_id);
        } else {
            fragmentTransaction.replace(R.id.root_view, baseFragment);
        }
        fragmentTransaction.commit();
    }
    protected void addFragment(BaseFragment baseFragment, String fragment_id, boolean isAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.root_view, baseFragment, fragment_id);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
