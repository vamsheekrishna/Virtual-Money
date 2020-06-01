package com.example.monet_transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.authentication.User;
import com.example.baseclasses.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AmulCard extends BaseActivity {

    private ImageView imageView;
    private TextView qrCodeText, qrCodeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_amul_card);
        imageView = findViewById(R.id.qr_code);
        qrCodeText = findViewById(R.id.qr_code_text);
        qrCodeName = findViewById(R.id.qr_code_name);
        // generateQRCode();
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        // String date = simpleDateFormat.format(new Date());
        // String text= new Date().getTime()+"";

        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

        // String date = simpleDateFormat.format(new Date());
        // String date = new Date().getTime()+"";

        String text="virtual_"+ User.getInstance().getAccountNO()+"_"+User.getInstance().getFullName();
        qrCodeText.setText(User.getInstance().getAccountNO().replaceAll("...", "$0 "));
        qrCodeName.setText(User.getInstance().getFullName());
        imageView.setImageBitmap(encodeAsBitmap(text, 800,800));
    }

    private void generateQRCode(String text, int width, int height) {
        // Whatever you need to encode in the QR code

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap encodeAsBitmap(String source, int width, int height) {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(source, BarcodeFormat.QR_CODE, width, height, null);
        } catch (IllegalArgumentException | WriterException e) {
            // Unsupported format
            return null;
        }

        final int w = result.getWidth();
        final int h = result.getHeight();
        final int[] pixels = new int[w * h];

        for (int y = 0; y < h; y++) {
            final int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        final Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);

        return bitmap;
    }
}
