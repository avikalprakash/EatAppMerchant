package organisation.app.eat.eatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText username_txt, mobile_txt, email_txt, password_txt, repassword_txt, spin_edtxt;
    Button signin_btn, mshowqrdetail, scannamecard_btn;
    int i = 0;
    String defaultValue;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username_txt=(EditText)findViewById(R.id.username_txt);
        mobile_txt=(EditText)findViewById(R.id.mobile_txt);
        email_txt=(EditText)findViewById(R.id.email_txt);
        password_txt=(EditText)findViewById(R.id.password_txt);
        repassword_txt=(EditText)findViewById(R.id.repassword_txt);
        signin_btn=(Button)findViewById(R.id.signin_btn);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spin_edtxt=(EditText)findViewById(R.id.spin_edtxt);
        scannamecard_btn=(Button)findViewById(R.id.scannamecard_btn);
        SharedPreferences sharedPref =  getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        defaultValue = sharedPref.getString("rstve",null);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.gender, R.layout.spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationCecck()){
                    register();
                }
            }
        });

        scannamecard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(RegisterActivity.this,NameCardFullScan.class));
              //  RegisterActivity.this.finish();
            }
        });

        mshowqrdetail = (Button) findViewById(R.id.showqrdetail_btn);
        mshowqrdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (defaultValue.equals("null")) {

                        Toast.makeText(RegisterActivity.this, "Please Select Answer", Toast.LENGTH_LONG).show();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage(defaultValue)
                                .setNegativeButton("ok", null)
                                .create()
                                .show();

                        i++;
                        if( i == 1) {
                            new Qrintro().execute();
                        }else{

                            // Toast.makeText(RegisterMember.this, "No", Toast.LENGTH_LONG).show();
                        }

                    }

                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this, "Please Scan Namecard First", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private boolean validationCecck(){
        boolean isValid=true;
        String username = username_txt.getText().toString();
        String mobile = mobile_txt.getText().toString();
        String email = email_txt.getText().toString();
        String pass = password_txt.getText().toString();
        String re_pass = repassword_txt.getText().toString();
        String gender = spinner.getSelectedItem().toString().trim();
        if (username.length()<=0){
            username_txt.requestFocus();
            username_txt.setError("This field is mandatory");
            isValid=false;
        }
        if (mobile.length()<=0){
            mobile_txt.requestFocus();
            mobile_txt.setError("This field is mandatory");
            isValid=false;
        }
        if (email.length()<=0){
            email_txt.requestFocus();
            email_txt.setError("This field is mandatory");
            isValid=false;
        }
        if (pass.length()<=0){
            password_txt.requestFocus();
            password_txt.setError("This field is mandatory");
            isValid=false;
        }
        if (re_pass.length()<=0){
            repassword_txt.requestFocus();
            repassword_txt.setError("This field is mandatory");
            isValid=false;
        }
        if (gender.equals("Gender")){
            spin_edtxt.requestFocus();
            spin_edtxt.setError("This field is mandatory");
            isValid=false;
        }

        return isValid;
    }

    public void register(){
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("loading...");
        progressDialog.show();
        String username = username_txt.getText().toString();
        String mobile = mobile_txt.getText().toString();
        String email = email_txt.getText().toString();
        String pass = password_txt.getText().toString();
        String gender = spinner.getSelectedItem().toString().trim();
        final String re_pass = repassword_txt.getText().toString();


        final String url = "http://condoassist2u.com/eatapp/API/merchantapi/staffsignup.php";

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("password", pass);
        map.put("re_password", re_pass);
        map.put("gender", gender);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    boolean check = response.getBoolean("error");
                    if (check){
                        String mg = response.getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage(mg)
                                .setNegativeButton("ok", null)
                                .create()
                                .show();
                    }else{
                        String mg = response.getString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage(mg)
                                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        RegisterActivity.this.finish();
                                    }
                                })
                                .create()
                                .show();
                    }
                }catch (Exception e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; chatset=utf-8");
                return header;
            }
        };
        jsonObjectRequest.setTag("tag");
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(jsonObjectRequest);
    }


    class Qrintro extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
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
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/cardUser.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("data", defaultValue);

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
