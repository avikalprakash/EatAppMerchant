///*
//package organisation.app.eat.eatapp;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.SparseArray;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.widget.TextView;
//import com.google.android.gms.vision.CameraSource;
//import com.google.android.gms.vision.Detector;
//import com.google.android.gms.vision.text.TextBlock;
//import com.google.android.gms.vision.text.TextRecognizer;
//import java.io.IOException;
//
//public class NameCardFullScan extends AppCompatActivity {
//
//    SurfaceView cameraview;
//    TextView textView;
//    CameraSource cameraSource;
//    final int permission = 1001;
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch(requestCode)
//        {
//
//            case permission:
//            {
//                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
//
//                    if (ActivityCompat.checkSelfPermission(NameCardFullScan.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//                    {
//                        return;
//                    }
//                    try {
//                        cameraSource.start(cameraview.getHolder());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//        }
//    }
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_name_card_full_scan);
//
//        cameraview = (SurfaceView) findViewById(R.id.surface_view);
//        textView = (TextView) findViewById(R.id.txtview);
//        TextRecognizer textRecognizer = null;
//
//        try {
//            textRecognizer = new TextRecognizer.Builder(NameCardFullScan.this).build();
//
//            if (!textRecognizer.isOperational())
//            {
//                AlertDialog.Builder builder = new AlertDialog.Builder(NameCardFullScan.this);
//                builder.setMessage("Please Update Your Google Play Service Or Release Some Spaces On Mobile")
//                        .setNegativeButton("ok", null)
//                        .create()
//                        .show();
//
//            }
//            else
//            {
//
//                cameraSource = new CameraSource.Builder(getApplicationContext(),textRecognizer)
//                        .setFacing(CameraSource.CAMERA_FACING_BACK)
//                        .setRequestedPreviewSize(1280, 1024)
//                        .setRequestedFps(2.0f)
//                        .setAutoFocusEnabled(true)
//                        .build();
//
//                cameraview.getHolder().addCallback(new SurfaceHolder.Callback() {
//                    @Override
//                    public void surfaceCreated(SurfaceHolder holder) {
//
//                        try {
//                            if (ActivityCompat.checkSelfPermission(NameCardFullScan.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                                ActivityCompat.requestPermissions(NameCardFullScan.this,
//                                        new String[]{Manifest.permission.CAMERA},
//                                        permission);
//
//                                return;
//                            }
//                            cameraSource.start(cameraview.getHolder());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//                    }
//
//                    @Override
//                    public void surfaceDestroyed(SurfaceHolder holder) {
//
//                        cameraSource.stop();
//
//                    }
//                });
//
//
//                textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
//                    @Override
//                    public void release() {
//
//                    }
//
//                    @Override
//                    public void receiveDetections(Detector.Detections<TextBlock> detections) {
//
//                        final SparseArray<TextBlock> items = detections.getDetectedItems();
//                        if(items.size()!= 0)
//                        {
//                            textView.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    StringBuilder stringBuilder = new StringBuilder();
//
//                                    for (int i = 0;i<items.size();i++){
//
//                                        TextBlock item  = items.valueAt(i);
//                                        stringBuilder.append(item.getValue());
//                                        stringBuilder.append("\n");
//
//                                    }
//                                    textView.setText(stringBuilder.toString());
//
//                                    String text = textView.getText().toString().trim();
//                                    SharedPreferences sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = sharedPref.edit();
//                                    editor.putString("rstve",text);
//                                    editor.commit();
//
//                                }
//                            });
//                        }
//
//                    }
//                });
//
//
//            } }catch (Exception e){
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(NameCardFullScan.this);
//            builder.setMessage("Please Update Your Google Play Service Or Release Some Spaces On Mobile")
//                    .setNegativeButton("ok", null)
//                    .create()
//                    .show();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        startActivity(new Intent(NameCardFullScan.this,RegisterActivity.class));
//        NameCardFullScan.this.finish();
//
//        super.onBackPressed();
//    }
//}
//*/
