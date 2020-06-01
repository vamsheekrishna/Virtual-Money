package com.example.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baseclasses.BaseFragment;
import com.example.monet_transfer.MoneyTransferActivity;
import com.example.monet_transfer.R;
import com.example.server.RetrofitInstance;
import com.example.server.api.interfaces.Login;
import com.example.server.api.pojo.POJOLogin;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            showDialog("","Please enter valid user id.");
        } else if (password.length() < 2) {
            showDialog("","Please enter valid password.");
        }else {
            Login loginAPI = RetrofitInstance.getInstance().create(Login.class);
            Call<POJOLogin> response = loginAPI.login(userID, password);
            response.enqueue(new Callback<POJOLogin>() {
                @Override
                public void onResponse(Call<POJOLogin> call, Response<POJOLogin> response) {
                    if(response.isSuccessful()) {
                        POJOLogin.User user = response.body().getUser();
                        User.getInstance().setUserID(user.getUserID());
                        User.getInstance().setFullName(user.getFullName());
                        User.getInstance().setAccountNumber(user.getAccountNO());
                        User.getInstance().setToken(user.getToken());
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), MoneyTransferActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            showDialog("",jObjError.getString("message"));
                        } catch (Exception e) {
                            showDialog("",e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<POJOLogin> call, Throwable t) {
                    Log.d("onFailure", "onFailure"+t.getMessage());
                }
            });
        }
        /* else if (userID.equals(UserData.getInstance().getMobileNumber((BaseActivity) Objects.requireNonNull(getActivity()))) &&
                password.equals(UserData.getInstance().getPassword((BaseActivity) Objects.requireNonNull(getActivity())))) {


        }*/
    }
}
