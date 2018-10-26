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

public class AmountEnterActivity extends AppCompatActivity {
    Button next;
    String Data = Help.DATA;
    String  amount="";
    String receipt_no;
    EditText amountText, receipt_no_text;
    String merchantId;
    String staff_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting layout
        setContentView(R.layout.activity_amount_enter);

        //setting Button and EditText
        next = (Button)findViewById(R.id.next);
        amountText=(EditText)findViewById(R.id.amount_text);
        receipt_no_text=(EditText)findViewById(R.id.receipt_no_text);

        //Getting Merchant ID
        merchantId = SaveUserId.getInstance(getApplicationContext()).getUserId();
        staff_id = SaveUserId.getInstance(getApplicationContext()).getStaffId();
        Log.d("staff_id", staff_id);
        // OnClickListener for Jump to QR Scanner
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting string from edittext
                amount =  amountText.getText().toString();
                receipt_no= receipt_no_text.getText().toString();

                //Checking required field blank or not by if condition
                if (amount.isEmpty()) {
                    //user_id.requestFocus();
                    Toast.makeText(getApplicationContext(), "please enter amount", Toast.LENGTH_LONG).show();
                }else if (receipt_no.isEmpty()) {
                    //user_id.requestFocus();
                    Toast.makeText(getApplicationContext(), "please enter receipt no.", Toast.LENGTH_LONG).show();
                }
                else {

                    // Sending QR scanner page by using Intent and put some strings
                    Intent i = new Intent(getApplicationContext(), AmountQrScannerHandler.class);
                    i.putExtra("amount", amount);
                    i.putExtra("receipt_no", receipt_no);
                    startActivity(i);
                    finish();
                }
            }
        });


        String key;
        if (Help.DATA.length() > 0) {
            if (!Data.equals("") || !Data.equals(null)) {
                key=getIntent().getStringExtra("key");

                // Handling Exception by try & catch
                try {
                    if (key.equals("") || key.equals(null)) {
                    } else {
                        if (key.equals("key")) {
                            try {
                                amount = getIntent().getStringExtra("amount");
                                receipt_no = getIntent().getStringExtra("receipt_no");
                                if (getIntent().getStringExtra("amount") != null && !amount.equals("")) {

                                    amount = getIntent().getStringExtra("amount");
                                    Log.d("outlet_Userid", amount );
                                    new QrintroWithAmount().execute();
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
    }

    class QrintroWithAmount extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AmountEnterActivity.this);
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
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/paymentqr.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("qr_code", Data);
                jsonObject.accumulate("price",amount);
                jsonObject.accumulate("receipt_no",receipt_no);
                jsonObject.accumulate("merchant_id", merchantId);
                jsonObject.accumulate("staff_id", staff_id);
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
                    finish();
                    Toast.makeText(getApplicationContext(), mg, Toast.LENGTH_LONG).show();
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
