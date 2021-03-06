package organisation.app.eat.eatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class GiftQrScannerHandler extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mscanner;
    Help handler;
    String Data;
    ProgressDialog dialog;
    String userID;
    String outlet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_id_qr_scanner_handler);
        userID = getIntent().getStringExtra("copun_status");
        Log.d("HELLO<><><><>","qr scanner called");
        mscanner= new ZXingScannerView(this);
        mscanner = new ZXingScannerView(this);
        setContentView(mscanner);
        mscanner.setResultHandler(this);

        
        mscanner.startCamera();
        Help.CAMERA_STATUS=1;
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("HELLO<><><><>", rawResult.getText()); // Prints scan results
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("HELLO<><><><>", rawResult.getBarcodeFormat().toString());
        Data = rawResult.getText();
        Log.d("dddddddddd", Data);

        String key="keyGift";
        Intent i=new Intent(GiftQrScannerHandler.this,MainActivity.class);
        i.putExtra("key", key);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

        mscanner.stopCameraPreview();
        mscanner.stopCamera();
        Help.DATA=Data;
    }
    public void onPause() {

        super.onPause();
        if(Help.CAMERA_STATUS==1){
            mscanner.stopCamera();
        }
        // Stop camera on pause
        Log.d("Hello<><><><>", "OnPause of scanner is called");
    }
}
