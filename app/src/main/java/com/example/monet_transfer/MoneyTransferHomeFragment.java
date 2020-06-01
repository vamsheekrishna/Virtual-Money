package com.example.monet_transfer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.authentication.User;
import com.example.baseclasses.BaseFragment;
import com.example.server.RetrofitInstance;
import com.example.server.api.interfaces.Login;
import com.example.server.api.pojo.POJOWalletBalance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoneyTransferHomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;

    private String mParam1;
    private String mParam2;
    private OnMoneyTransferListener mListener;

    public MoneyTransferHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnMoneyTransferListener) {
            mListener = (OnMoneyTransferListener) context;
        }
    }

    public static MoneyTransferHomeFragment newInstance(String param1, String param2) {
        MoneyTransferHomeFragment fragment = new MoneyTransferHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                    getContext(), Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        return inflater.inflate(R.layout.fragment_money_transfer_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.name);

        TextView balance = view.findViewById(R.id.balance);
        view.findViewById(R.id.qr_scanner_root).setOnClickListener(this);
        view.findViewById(R.id.qr_generator).setOnClickListener(this);

        Login loginAPI = RetrofitInstance.getInstance().create(Login.class);
        Call<POJOWalletBalance> balanceAPI = loginAPI.walletBalance(User.getInstance().getToken(), User.getInstance().getAccountNO() ,User.getInstance().getUserID());
        balanceAPI.enqueue(new Callback<POJOWalletBalance>() {
            @Override
            public void onResponse(Call<POJOWalletBalance> call, Response<POJOWalletBalance> response) {
                try {
                    if(response.isSuccessful()) {
                        POJOWalletBalance.Data data = response.body().getData();
                        User.getInstance().setAccountNumber(data.getAccountNumber());
                        User.getInstance().setWalletID(data.getWalletID());
                        User.getInstance().setAmount(data.getAmount());
                        User.getInstance().setDisplayName(data.getDisplayName());
                        name.setText(User.getInstance().getFullName());
                        balance.setText(data.getAmount());
                    }
                }catch (Exception e) {
                    showDialog("", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<POJOWalletBalance> call, Throwable t) {
                showDialog("", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.qr_scanner_root:
                mListener.goToQRScanner();
                break;
            case R.id.qr_generator:
                mListener.generateQRCode();
                break;
        }

    }
}
