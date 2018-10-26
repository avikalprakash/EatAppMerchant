package organisation.app.eat.eatapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentQRList extends AppCompatActivity {
    ArrayList<PaymentPojo> promotionDetailses=new ArrayList<>();
    private String url = "http://condoassist2u.com/eatapp/API/merchantapi/report_payment_list.php";
    ListView mList;
    TextView backText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_qrlist);
        backText=(TextView)findViewById(R.id.backText);
        mList = (ListView)findViewById(R.id.listIntroducer);
        loadIntroducerList();
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadIntroducerList() {
        //getting the progressbar
        final ProgressDialog progressDialog = new ProgressDialog(PaymentQRList.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        promotionDetailses.clear();

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                        progressDialog.dismiss();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            String check = obj.getString("error");
                            if (check.equals("false")){

                                JSONArray jArray = obj.getJSONArray("message");

                                //now looping through all the elements of the json array
                                for (int i = 0; i < jArray.length(); i++) {
                                    //getting the json object of the particular index inside the array
                                    JSONObject jObject = jArray.getJSONObject(i);
                                    PaymentPojo introducerPojo = new PaymentPojo();
                                    introducerPojo.setUser_name(jObject.getString("user_name"));
                                    introducerPojo.setQr_image(jObject.getString("qr_image"));
                                    introducerPojo.setBranch_name(jObject.getString("branch_name"));
                                    introducerPojo.setAmount(jObject.getString("amount"));
                                    introducerPojo.setDate(jObject.getString("date"));

                                    promotionDetailses.add(introducerPojo);

                                }
                                PaymentListAdapter categoryAdapter = new PaymentListAdapter(PaymentQRList.this, promotionDetailses);
                                mList.setAdapter(categoryAdapter);

                            }else if (check.equals("true")){

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
