package com.example.monet_transfer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.baseclasses.BaseActivity;
import com.example.baseclasses.DialogFragment;

public class MoneyTransferActivity extends BaseActivity implements OnMoneyTransferListener {
    //Bitmap myBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.base_layour);
        if (savedInstanceState == null) {
            addFragment(MoneyTransferHomeFragment.newInstance("", ""), "MoneyTransferHomeFragment", false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void goToTransferMoneyHome() {
        replaceFragment(MoneyTransferHomeFragment.newInstance("", ""), "MoneyTransferHomeFragment", true);
    }

    @Override
    public void goToQRScanner() {
        replaceFragment(QRScannerFragment.newInstance("", ""), "QRScannerFragment", true);
    }

    @Override
    public void goToEnterMoney(String scannedID) {
        replaceFragment(EnterAmountFragment.newInstance(scannedID, ""), "EnterAmountFragment", true);
    }

    @Override
    public void goToEnterPassCode(String id, int transferRefNumber, String transferAmount, String receiverCardID) {
        replaceFragment(ConformPinFragment.newInstance(id, transferRefNumber, transferAmount,receiverCardID), "ConformPinFragment", true);
    }

    @Override
    public void generateQRCode() {
        startActivity(new Intent(getApplicationContext(), AmulCard.class));
        //replaceFragment(GenerateVirtualCard.newInstance("", ""), "GenerateVirtualCard", true);
    }

    @Override
    public void showDialog() {
        //        FragmentTransaction ft = Objects.requireNonNull(getSupportFragmentManager().beginTransaction());
        //        Fragment prev = Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("custom_dialog"));
        //        if (prev != null) {
        //            ft.remove(prev);
        //        }
        //        ft.addToBackStack(null);

        DialogFragment dialogFragment = DialogFragment.newInstance(false, "");
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager().beginTransaction(), "dialog fragment");
        // progressBar.dismiss();
    }
    @Override
    public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag("EnterAmountFragment");
        if (fragment != null) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
