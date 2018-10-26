package organisation.app.eat.eatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CoponNumberEnter extends AppCompatActivity {
    EditText coponText;
    Button ok;
    String  copun_status="";
    String Data = Help.DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copon_number_enter);
        ok=(Button) findViewById(R.id.ok);
        coponText=(EditText)findViewById(R.id.copon_code);

        String key;
        if (Help.DATA.length() > 0) {
            if (!Data.equals("") || !Data.equals(null)) {
                key=getIntent().getStringExtra("key");
                try {
                    if (key.equals("") || key.equals(null)) {
                    } else {
                        if (key.equals("key")) {
                            try {
                                copun_status = getIntent().getStringExtra("copun_status");
                                if (getIntent().getStringExtra("copun_status") != null && !copun_status.equals("")) {

                                    copun_status = getIntent().getStringExtra("copun_status");
                                    new QrintroWithUserId().execute();

                                }

                            }catch (Exception e){}
                            //   Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
                        } else if (key.equals("key2")) {
                            //  Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){}
            }
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copun_status =  coponText.getText().toString();
                if (copun_status.isEmpty()){
                    coponText.setError("Please enter Copon Status");
                }
                else {

                    Intent i = new Intent(getApplicationContext(), CoponQrScannerHandler.class);
                    i.putExtra("copun_status", copun_status);
                    startActivity(i);
                    finish();
                }

            }
        });
    }


    class QrintroWithUserId extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CoponNumberEnter.this);
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
                jsonObject.accumulate("coupon_status1", copun_status);
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Scan Successfully", Toast.LENGTH_LONG).show();
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
}
