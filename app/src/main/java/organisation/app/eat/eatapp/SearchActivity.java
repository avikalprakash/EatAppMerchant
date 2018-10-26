package organisation.app.eat.eatapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.util.ArrayList;

import static android.app.AlertDialog.*;

public class SearchActivity extends AppCompatActivity {
    Activity activity;
    String id="";
    String user_name="";
    String mobile="";
    String email="";
    String gender="";
    String photo="";
    int i=0 ;
    ListView listView;
    ImageView back;
    AutoCompleteTextView autocompletedtextview;
    ArrayList<SearchPojo> promotionDetailses=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity=this;
        autocompletedtextview=(AutoCompleteTextView)findViewById(R.id.autocompletedtextview);
        listView = (ListView) findViewById(R.id.listView);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        autocompletedtextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                promotionDetailses.clear();
                Log.d("searcHItems","**");
                //  loadData(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 1) {

                    promotionDetailses.clear();
                    Log.d("searcHItems","**@");
                    new SearchMember().execute();
                }else if (charSequence.toString().length() == 1 || charSequence.toString().length() == 0){
                    promotionDetailses.clear();
                    Log.d("search","**************");
                   // hideSoftKeyboard(activity);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                promotionDetailses.clear();
                Log.d("searcHItems","**$");
                //loadData(editable);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), SearchDetails.class);
                i.putExtra("id", promotionDetailses.get(position).getUser_id());
                i.putExtra("user_name", promotionDetailses.get(position).getUser_name());
                i.putExtra("mobile", promotionDetailses.get(position).getMobile());
                i.putExtra("email", promotionDetailses.get(position).getEmail());
                i.putExtra("gender", promotionDetailses.get(position).getGender());
                i.putExtra("photo", promotionDetailses.get(position).getPhoto());
                startActivity(i);
            }
        });

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    class SearchMember extends AsyncTask<String, Void, String> {
        String user = autocompletedtextview.getText().toString();
        private ProgressDialog pDialog;

        //  String qr = mresultTextView.getText().toString().trim();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            promotionDetailses.clear();
        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://condoassist2u.com/eatapp/API/merchantapi/searchUser.php");  //http://cloud9.condoassist2u.com
                httpPost.setHeader("Content-type", "application/json");
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("keyword", user);
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
                boolean check  = objone.getBoolean("error");
                if (check){
                    AlertDialog.Builder builder = new Builder(SearchActivity.this);
                    builder.setMessage("Member Not Found")
                            .setNegativeButton("ok", null)
                            .create()
                            .show();
                    autocompletedtextview.setText("");
                }else {
                    JSONArray jsonArray = objone.getJSONArray("message");
                    for (int i=0;jsonArray.length()>i;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id=jsonObject.getString("id");
                        user_name=jsonObject.getString("name");
                        mobile=jsonObject.getString("mobile");
                        email=jsonObject.getString("email");
                        gender=jsonObject.getString("gender");
                        photo=jsonObject.getString("user_pic");
                        SearchPojo searchPojo = new SearchPojo();
                        searchPojo.setUser_id(id);
                        searchPojo.setUser_name(user_name);
                        searchPojo.setEmail(email);
                        searchPojo.setMobile(mobile);
                        searchPojo.setGender(gender);
                        searchPojo.setPhoto(photo);
                        promotionDetailses.add(searchPojo);
                        SearchListAdaptor qrListAdaptor = new SearchListAdaptor(SearchActivity.this, promotionDetailses);
                        listView.setAdapter(qrListAdaptor);
                    }
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

