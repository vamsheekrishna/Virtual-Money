package com.example.monet_transfer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.baseclasses.BaseFragment;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static android.content.Context.VIBRATOR_SERVICE;

public class QRScannerFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // private TextView textView;
    private BarcodeDetector detector;
    private CameraSource cameraSource;
    private OnMoneyTransferListener mListener;
    private boolean isSuccess;
    private SurfaceView surfaceView;

    public QRScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnMoneyTransferListener) {
            mListener = (OnMoneyTransferListener) context;
        }
    }

    public static QRScannerFragment newInstance(String param1, String param2) {
        QRScannerFragment fragment = new QRScannerFragment();
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
        return inflater.inflate(R.layout.fragment_q_r_scanner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(getActivity(), detector).setRequestedPreviewSize(640,480)
                .build();
        //ImageView myImageView = findViewById(R.id.imgview);
        /*myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.qr_code);*/
        //myImageView.setImageBitmap(myBitmap);
        /*textView = view.findViewById(R.id.txtContent);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                        assert vibrator != null;
                        vibrator.vibrate(new long[]{0, 200} , -1);
                        textView.setText("");
                    }
                });
            }
        });*/

        showPreview(view);
    }

    private void showPreview(View view) {
        surfaceView = view.findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!=
                        PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                try {
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                //getBarcodeData();
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size()!=0 ) {
                    final Barcode thisCode = barcodes.valueAt(0);
                    if(thisCode.rawValue.contains("virtual_") && !isSuccess) {
                        // cameraSource.stop();
                        //detector.release();
                        //isSuccess = true;

                        Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
                        assert vibrator != null;
                        vibrator.vibrate(new long[]{0, 200, 300,400} , -1);
                        mListener.goToEnterMoney(thisCode.rawValue);

                    }
//                    textView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                                                        // textView.setText(thisCode.rawValue);
//                        }
//                    });
                }
            }
        });
    }

}
