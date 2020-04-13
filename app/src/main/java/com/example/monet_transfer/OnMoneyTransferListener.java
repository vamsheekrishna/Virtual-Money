package com.example.monet_transfer;

public interface OnMoneyTransferListener {
    void goToTransferMoneyHome();
    void goToQRScanner();
    void goToEnterMoney(String scannedID);
    void goToEnterPassCode(Long amount, String mScannedID);
    void showDialog();
}
