package com.example.authentication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.baseclasses.BaseActivity;
import com.example.monet_transfer.R;

public class AuthenticationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.base_layour);
        addFragment(LoginFragment.newInstance("", ""), "LoginFragment", false);
    }
}
