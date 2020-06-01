package com.example.monet_transfer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.authentication.User;
import com.example.baseclasses.BaseFragment;
import com.example.server.RetrofitInstance;
import com.example.server.api.interfaces.Transfer;
import com.example.server.api.pojo.POJOVerification;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConformPinFragment extends BaseFragment {
    private static final String ARG_ID = "param1";
    private static final String ARG_TRANSACTION_NUMBER = "param2";
    private static final String ARG_AMOUNT = "param3";
    private static final String ARG_RECEIVER_ID = "param4";

    // TODO: Rename and change types of parameters
    private String mAmount;
    private String mScannedID;
    int mTransactionReferenceID;
    String mTPin;


    private OnMoneyTransferListener mListener;
    private MediaPlayer mCorrect, mWrong;
    public ConformPinFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnMoneyTransferListener) {
            mListener = (OnMoneyTransferListener) context;
        }
    }
    public static ConformPinFragment newInstance(String id, int transferRefNumber, String transferAmount, String receiverCardID) {
        ConformPinFragment fragment = new ConformPinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        args.putInt(ARG_TRANSACTION_NUMBER, transferRefNumber);
        args.putString(ARG_AMOUNT, transferAmount);
        // args.putString(ARG_RECEIVER_ID, receiverCardID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAmount = getArguments().getString(ARG_AMOUNT);
            mScannedID = getArguments().getString(ARG_ID);
            mTransactionReferenceID = getArguments().getInt(ARG_TRANSACTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCorrect = MediaPlayer.create(getActivity(), R.raw.correct);
        mWrong = MediaPlayer.create(getActivity(), R.raw.wrong);

        return inflater.inflate(R.layout.fragment_conform_pin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // mListener.showDialog();

        String[] id = mScannedID.split("_");
        String title = String.format(getActivity().getString(R.string.paying_to_rs),mAmount+"/-", id[id.length-1]);
        ((TextView)view.findViewById(R.id.pay_to)).setText(title);

        final ImageView imageView = view.findViewById(R.id.success);
        imageView.setVisibility(View.GONE);
        view.findViewById(R.id.pin).setOnKeyListener((view1, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String text = ((EditText) view1).getText().toString();
                    checkTPIN(imageView, text);
                    return true;
                }
            }
            return false;

//                if (pressedKey == keyEvent.KEYCODE_ENTER) {
//                    String text = ((EditText) view).getText().toString();
//                    // mListener.showDialog();
//
//                }
//                return false;
        });
//                .setOnKeyListener(new OnKeyListener(){
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event){
//                if(keyCode == event.KEYCODE_ENTER){
//                    //do what you want
//                }
//            }
//        });
    }

    private void checkTPIN(final ImageView imageView, String text) {

        if(text.length()>3) {
            Transfer transferAPI = RetrofitInstance.getInstance().create(Transfer.class);
            String temp =mScannedID.split("_")[1];
            transferAPI.verification(User.getInstance().getToken(), mTransactionReferenceID, temp,mAmount, text, User.getInstance().getUserID()).enqueue(new Callback<POJOVerification>() {
                @Override
                public void onResponse(Call<POJOVerification> call, Response<POJOVerification> response) {
                    if(response.isSuccessful()) {
                        mCorrect.start();
                        // UserData.getInstance().updateBalance(mAmount, getActivity());
                        imageView.setVisibility(View.VISIBLE);
                        imageView.animate().scaleX(0).scaleY(0).rotation(180).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                imageView.animate().scaleX(1).scaleY(1).rotation(0).setDuration(500).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(), MoneyTransferActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                    } else {
                        mWrong.start();
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            showDialog("",jObjError.getString("message"));
                        } catch (Exception e) {
                            showDialog("",e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<POJOVerification> call, Throwable t) {
                    showDialog("",t.getMessage());
                }
            });
        } else {
            showDialog("","Please enter valid t-pin");
        }
    }
}
