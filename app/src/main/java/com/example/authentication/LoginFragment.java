package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baseclasses.BaseActivity;
import com.example.baseclasses.BaseFragment;
import com.example.baseclasses.UserData;
import com.example.monet_transfer.MoneyTransferActivity;
import com.example.monet_transfer.R;

import java.util.Objects;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText userName, textPassword;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.login_button).setOnClickListener(this);
        userName = view.findViewById(R.id.user_name);
        textPassword = view.findViewById(R.id.password);
    }

    @Override
    public void onClick(View view) {
        String userID = userName.getText().toString();
        String password = textPassword.getText().toString();

        if(userID.length() <10) {
            Toast.makeText(getActivity(), "Please enter valid user id.", Toast.LENGTH_LONG).show();
        } else if (password.length() < 4) {
            Toast.makeText(getActivity(), "Please enter valid password.", Toast.LENGTH_LONG).show();
        } else if (userID.equals(UserData.getInstance().getMobileNumber((BaseActivity) Objects.requireNonNull(getActivity()))) &&
                password.equals(UserData.getInstance().getPassword((BaseActivity) Objects.requireNonNull(getActivity())))) {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), MoneyTransferActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Incorrect user id or password.", Toast.LENGTH_LONG).show();
        }
    }
}
