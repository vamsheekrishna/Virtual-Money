package com.example.monet_transfer;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.authentication.User;
import com.example.baseclasses.BaseFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;

public class GenerateVirtualCard extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private TextView qrCodeText, qrCodeName;

    public GenerateVirtualCard() {
        //
    }
    public static GenerateVirtualCard newInstance(String param1, String param2) {
        GenerateVirtualCard fragment = new GenerateVirtualCard();
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
        return inflater.inflate(R.layout.fragment_generate_virtual_card, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.qr_code);
        qrCodeText = view.findViewById(R.id.qr_code_text);
        qrCodeName = view.findViewById(R.id.qr_code_name);
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
        imageView.setImageBitmap(encodeAsBitmap(text, 600,600));
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
