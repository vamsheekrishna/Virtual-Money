package com.example.monet_transfer;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baseclasses.BaseActivity;
import com.example.baseclasses.BaseFragment;
import com.example.baseclasses.UserData;

public class EnterAmountFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mScannedID;
    private String mParam2;
    private OnMoneyTransferListener mListener;
    private EditText amount;

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
            mScannedID = getArguments().getString(ARG_PARAM1);
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
        ((TextView)view.findViewById(R.id.name)).setText(UserData.getInstance().getFirstName((BaseActivity) getActivity()));
        ((TextView)view.findViewById(R.id.balance)).setText(UserData.getInstance().getBalance((BaseActivity) getActivity()));
        amount = view.findViewById(R.id.amount);
        String[] id = mScannedID.split("_");
        String title = String.format(getActivity().getString(R.string.paying_to), id[1]);
        ((TextView)view.findViewById(R.id.pay_to)).setText(title);
        view.findViewById(R.id.next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Long value = Long.valueOf(amount.getText().toString());
        if (value>0) {
            mListener.goToEnterPassCode(value, mScannedID);
        } else {
            Toast.makeText(getActivity(), "Please enter valid amount", Toast.LENGTH_LONG).show();
        }
    }
}
