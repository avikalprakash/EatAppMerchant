package organisation.app.eat.eatapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import organisation.app.eat.eatapp.FCM.TokenSave;

public class LoginActivity extends AbsRuntimePermission {
    Button login;
    int REQUEST_PERMISSION = 10 ;
    Button msignupbtn,mloginbtn;
    EditText musernametxt,mpasswordtxt;
    UserSessionManager session;
    String   check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.login);
        session = new UserSessionManager(getApplicationContext());
        musernametxt = (EditText) findViewById(R.id.Username_txt);
        mpasswordtxt = (EditText) findViewById(R.id.Password_txt);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                if (isvalidinput()) {
                    new LloginAsync().execute();
                }
            }
        });
        requestAppPermissions(new String[]{
                        Manifest.permission.CAMERA},
                R.string.msg, REQUEST_PERMISSION);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    class LloginAsync extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String username = musernametxt.getText().toString().trim();
        String password = mpasswordtxt.getText().toString().trim();
        final String tokenid = TokenSave.getInstance(LoginActivity.this).getDeviceToken();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
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
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/staff_login.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("staf_name", username);
                jsonObject.accumulate("password", password);
                jsonObject.accumulate("reg_token", tokenid);

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
         //   pDialog.dismiss();
            try {
                JSONObject objone = null;
                try {
                    objone = new JSONObject(json);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
               // boolean check  = false;


                try {
                    check = objone.getString("error");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if(check.equals("true")) {

                    String message= objone.getString("message");

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(message)
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                }else if(check.equals("false")){

                    String userid = null;
                    String merchant_id="";
                    try {
                        userid = objone.getString("staff_id");
                        merchant_id = objone.getString("merchant_id");

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    session.createUserLoginSession(userid);
                    SaveUserId.getInstance(getApplicationContext()).saveuserId(merchant_id);
                    SaveUserId.getInstance(getApplicationContext()).savestaffId(userid);

                    new UpdateToken().execute();


                    }
            } catch (Exception e) {
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

    private boolean isvalidinput(){

        boolean isValid = true;
        String username = musernametxt.getText().toString().trim();
        String password = mpasswordtxt.getText().toString().trim();

        if(username.length() <=0) {
            musernametxt.requestFocus();
            musernametxt.setError("This Field Is Mandatory");
            isValid = false;
        }

        else if(password.length() <=0){
            mpasswordtxt.requestFocus();
            mpasswordtxt.setError("This Field Is Mandatory");
            isValid = false;
        }

        return isValid;

    }



    class UpdateToken extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        String staff_id = SaveUserId.getInstance(getApplicationContext()).getStaffId();
        final String tokenid = TokenSave.getInstance(LoginActivity.this).getDeviceToken();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
           /* pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/update_reg_token.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("staff_id", staff_id);
                jsonObject.accumulate("reg_token", tokenid);

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
                JSONObject objone = null;
                try {
                    objone = new JSONObject(json);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                // boolean check  = false;


                try {
                    check = objone.getString("error");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if(check.equals("true")) {

                    String message= objone.getString("message");

                 //   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }else if(check.equals("false")){
                    String userid = null;
                    String merchant_id="";
                    try {
                        String message= objone.getString("message");
                    //    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    Intent showadintnt = new Intent(LoginActivity.this, MainActivity.class);
                    showadintnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showadintnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(showadintnt);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
