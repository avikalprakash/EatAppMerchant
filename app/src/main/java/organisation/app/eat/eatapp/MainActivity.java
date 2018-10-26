package organisation.app.eat.eatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout scanQr, SearchMember, Register, paid_item_qr_list, redeemed_prize,
            redeemed_paidItem, payment, coupon_qr_list, payment_qr_list, introducer_qr_list,
            scan_gift_qr, gift_qr_list;
    UserSessionManager session;
    String Data = Help.DATA;
    String  copun_status="";
    ImageView logoutImageView;
    String merchantId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanQr = (LinearLayout)findViewById(R.id.scan_qr);
        SearchMember=(LinearLayout)findViewById(R.id.search_member);
        Register = (LinearLayout)findViewById(R.id.register);
        logoutImageView =(ImageView) findViewById(R.id.logoutImageView);
        paid_item_qr_list=(LinearLayout)findViewById(R.id.paid_item_qr_list);
        redeemed_prize=(LinearLayout)findViewById(R.id.redeemed_prize);
        redeemed_paidItem=(LinearLayout)findViewById(R.id.redeemed_paidItem);
        payment=(LinearLayout)findViewById(R.id.payment);
        coupon_qr_list = (LinearLayout) findViewById(R.id.coupon_qr_list);
        introducer_qr_list =(LinearLayout)findViewById(R.id.introducer_qr_list);
        payment_qr_list = (LinearLayout)findViewById(R.id.payment_qr_list);
        scan_gift_qr = (LinearLayout)findViewById(R.id.scan_gift_qr);
        gift_qr_list = (LinearLayout)findViewById(R.id.gift_qr_list);
        scanQr.setOnClickListener(this);
        SearchMember.setOnClickListener(this);
        Register.setOnClickListener(this);
        logoutImageView.setOnClickListener(this);
        paid_item_qr_list.setOnClickListener(this);
        redeemed_prize.setOnClickListener(this);
        redeemed_paidItem.setOnClickListener(this);
        payment.setOnClickListener(this);
        coupon_qr_list.setOnClickListener(this);
        introducer_qr_list.setOnClickListener(this);
        payment_qr_list.setOnClickListener(this);
        gift_qr_list.setOnClickListener(this);
        scan_gift_qr.setOnClickListener(this);
        merchantId = SaveUserId.getInstance(getApplicationContext()).getUserId();
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }

        String key;
        if (Help.DATA.length() > 0) {
            if (!Data.equals("") || !Data.equals(null)) {
                key=getIntent().getStringExtra("key");
                try {
                    if (key.equals("") || key.equals(null)) {
                    } else {
                        if (key.equals("key")) {
                            try {
                                    new QRCoponScan().execute();

                            }catch (Exception e){}
                            //   Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
                        } else if (key.equals("keyPaid")) {
                            try {
                                new QRPaidItemScan().execute();

                            }catch (Exception e){}
                        }else if (key.equals("keyIntroducer")) {
                            try {
                                new QrintroWithUserId().execute();

                            }catch (Exception e){}
                        }else if (key.equals("keyGift")) {
                            try {
                                new ScanGiftQR().execute();

                            }catch (Exception e){}
                        }
                    }
                }catch (Exception e){}
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_qr:
             startActivity(new Intent(getApplicationContext(), UserIdQrScannerHandler.class));
                break;

            case R.id.search_member:
             startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                break;

            case R.id.register:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;

            case R.id.logoutImageView:
                AlertDialog();
                break;

            case R.id.paid_item_qr_list:
                startActivity(new Intent(getApplicationContext(), PaidItemListActivity.class));
                break;
            case R.id.redeemed_prize:
                Intent i = new Intent(getApplicationContext(), CoponQrScannerHandler.class);
                startActivity(i);
                break;
            case R.id.redeemed_paidItem:
                Intent intent = new Intent(getApplicationContext(), PaidItemQrScannerHandler.class);
                startActivity(intent);
                break;
            case R.id.payment:
                Intent intent2 = new Intent(getApplicationContext(), AmountEnterActivity.class);
                startActivity(intent2);
                break;
            case R.id. coupon_qr_list:
                startActivity(new Intent(getApplicationContext(), CouponQRList.class));
                break;

            case R.id.payment_qr_list:

                startActivity(new Intent(getApplicationContext(), PaymentQRList.class));
                break;

            case R.id.introducer_qr_list:
                  startActivity(new Intent(getApplicationContext(), IntroducerQRList.class));
                break;
            case R.id.scan_gift_qr:
                 startActivity(new Intent(getApplicationContext(), GiftQrScannerHandler.class));
                break;
            case R.id.gift_qr_list:
                 startActivity(new Intent(getApplicationContext(), GiftQRList.class));
                break;
        }
    }

    public void AlertDialog(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.logout_alert, null);
        final TextView yes = (TextView) subView.findViewById(R.id.yes);
        final TextView no = (TextView)subView.findViewById(R.id.no);

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setView(subView);
        final AlertDialog alertDialog = builder1.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                MainActivity.this.finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }


    class QRCoponScan extends AsyncTask<String, Void, String> {

      //  String merchant_id = SaveUserId.getInstance(MainActivity.this).getUserId();

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/confirmCoupon.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("qr_code", Data);
                jsonObject.accumulate("merchant_id", merchantId);
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();
                Log.d("espone",exception.toString());
            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                String check  = objone.getString("error");
                if(check.equals("true")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
                }if(check.equals("false")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
                }
                if(check.equals("qrerror")){

                    Toast.makeText(getApplicationContext(), "Qr code already Scaned", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private String readadsResponse(HttpResponse httpResponse) {
        InputStream is = null;
        String return_text = "";
        try {
            is = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return_text = sb.toString();
            Log.d("return1230", "" + return_text);
        } catch (Exception e) {

        }
        return return_text;
    }


    class QRPaidItemScan extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
      //  String merchant_id = SaveUserId.getInstance(MainActivity.this).getUserId();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/confirmpaiditem.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("qr_code", Data);
                jsonObject.accumulate("merchant_id", merchantId);
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();
                Log.d("espone",exception.toString());
            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                String check  = objone.getString("error");
                if(check.equals("true")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
                }if(check.equals("false")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
                }
                if(check.equals("qrerror")){

                    Toast.makeText(getApplicationContext(), "Qr code already Scaned", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class QrintroWithUserId extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        String merchant_id = SaveUserId.getInstance(MainActivity.this).getUserId();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/confirmQr.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("qr_code", Data);
                jsonObject.accumulate("merchant_id", merchant_id);
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                String check  = objone.getString("error");
                if(check.equals("true")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
                }if(check.equals("false")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();

                }

                if(check.equals("qrerror")){
                     String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class ScanGiftQR extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;
        String merchant_id = SaveUserId.getInstance(MainActivity.this).getUserId();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/gift_redeem.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("qr_code", Data);
                jsonObject.accumulate("merchant_id", merchant_id);
                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readadsResponse(httpResponse);
                Log.d("tag1", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();

                Log.d("espone",exception.toString());

            }

            return s;

        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                JSONObject objone = new JSONObject(json);
                String check  = objone.getString("error");
                if(check.equals("true")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
                }if(check.equals("false")) {
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();

                }

                if(check.equals("qrerror")){
                    String mg = objone.getString("message");
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
