package com.example.monet_transfer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.authentication.User;
import com.example.baseclasses.BaseFragment;
import com.example.server.RetrofitInstance;
import com.example.server.api.interfaces.Transfer;
import com.example.server.api.pojo.POJOTransferRequest;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterAmountFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String[] mScannedID;
    private String mParam2;
    private OnMoneyTransferListener mListener;
    private EditText amount;
    private String id;

    public EnterAmountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnMoneyTransferListener) {
            mListener = (OnMoneyTransferListener) context;
        }
    }
    public static EnterAmountFragment newInstance(String param1, String param2) {
        EnterAmountFragment fragment = new EnterAmountFragment();
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
            id = getArguments().getString(ARG_PARAM1);
            mScannedID = id.split("_");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_amount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.name)).setText(User.getInstance().getFullName());
        ((TextView)view.findViewById(R.id.balance)).setText(User.getInstance().getAmount());
        amount = view.findViewById(R.id.amount);
        String title = String.format(getActivity().getString(R.string.paying_to), mScannedID[mScannedID.length-1]);
        ((TextView)view.findViewById(R.id.pay_to)).setText(title);
        view.findViewById(R.id.next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (amount.getText().toString().length()>0) {
            int value = Integer.valueOf(amount.getText().toString());
            Transfer transferAPI = RetrofitInstance.getInstance().create(Transfer.class);
            User user = User.getInstance();
            transferAPI.transferRequest(user.getToken(), user.getAccountNO(), value,"Description",mScannedID[1],user.getUserID()).enqueue(new Callback<POJOTransferRequest>() {
                @Override
                public void onResponse(Call<POJOTransferRequest> call, Response<POJOTransferRequest> response) {
                    if(response.isSuccessful()) {
                        POJOTransferRequest.Data data = response.body().getData();
                        mListener.goToEnterPassCode(id, data.getTransferRefNumber(),data.getTransAmt(), data.getReceiverCardID());
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            showDialog("",jObjError.getString("message"));
                        } catch (Exception e) {
                            showDialog("", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<POJOTransferRequest> call, Throwable t) {

                }
            });
        } else {
            showDialog("","Please enter the amount");
        }
    }
}
