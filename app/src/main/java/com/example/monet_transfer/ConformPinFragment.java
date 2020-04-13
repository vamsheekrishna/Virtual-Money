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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baseclasses.BaseFragment;
import com.example.baseclasses.UserData;

public class ConformPinFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Long mAmount;
    private String mScannedID;
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
    public static ConformPinFragment newInstance(Long amount, String scannedID) {
        ConformPinFragment fragment = new ConformPinFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, amount);
        args.putString(ARG_PARAM2, scannedID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAmount = getArguments().getLong(ARG_PARAM1);
            mScannedID = getArguments().getString(ARG_PARAM2);
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
        String title = String.format(getActivity().getString(R.string.paying_to_rs),mAmount+"/-", id[1]);
        ((TextView)view.findViewById(R.id.pay_to)).setText(title);

        final ImageView imageView = view.findViewById(R.id.success);
        imageView.setVisibility(View.GONE);
        view.findViewById(R.id.pin).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String text = ((EditText) view).getText().toString();
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
            }
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
        if(UserData.getInstance().getTPIN(getActivity()).equals(text)) {
            mCorrect.start();
            UserData.getInstance().updateBalance(mAmount, getActivity());
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
            Toast.makeText(getActivity(), "Entered incorrect T-PIN.", Toast.LENGTH_LONG).show();
        }
    }
}
