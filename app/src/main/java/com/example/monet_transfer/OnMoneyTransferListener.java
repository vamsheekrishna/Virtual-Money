package com.example.monet_transfer;

public interface OnMoneyTransferListener {
    void goToTransferMoneyHome();
    void goToQRScanner();
    void goToEnterMoney(String scannedID);
    void goToEnterPassCode(String id, int transferRefNumber, String transferAmount, String receiverCardID);
    void generateQRCode();
    void showDialog();
}
