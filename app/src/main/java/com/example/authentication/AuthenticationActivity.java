package com.example.authentication;

import android.os.Bundle;

import com.example.baseclasses.BaseActivity;
import com.example.monet_transfer.R;

public class AuthenticationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layour);
        addFragment(LoginFragment.newInstance("", ""), "", false);
    }
}
