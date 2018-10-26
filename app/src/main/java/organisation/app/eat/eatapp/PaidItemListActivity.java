package organisation.app.eat.eatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaidItemListActivity extends AppCompatActivity {


    ArrayList<PaidItemPojo> promotionDetailses=new ArrayList<>();
    private String url = "http://condoassist2u.com/eatapp/API/merchantapi/report_paid_item_list.php";
    ListView mList;
    TextView backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_item_list);
        backText=(TextView)findViewById(R.id.backText);
        mList = (ListView)findViewById(R.id.listPaidItem);
        loadHeroList();
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadHeroList() {
        //getting the progressbar
        final ProgressDialog progressDialog = new ProgressDialog(PaidItemListActivity.this);
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
                                    PaidItemPojo paidItemPojo = new PaidItemPojo();
                                    paidItemPojo.setP_order_id(jObject.getString("p_order_id"));
                                    paidItemPojo.setProduct_id(jObject.getString("product_id"));
                                    paidItemPojo.setQr_image(jObject.getString("qr_image"));
                                    paidItemPojo.setUser_id(jObject.getString("user_id"));
                                    paidItemPojo.setInvoice_number(jObject.getString("invoice_number"));
                                    paidItemPojo.setUser_name(jObject.getString("user_name"));
                                    paidItemPojo.setProduct_name(jObject.getString("product_name"));
                                    paidItemPojo.setRedeem_date((jObject.getString("redeem_date")));


                                    promotionDetailses.add(paidItemPojo);

                                }
                                PaidItemQRListAdaptor categoryAdapter = new PaidItemQRListAdaptor(PaidItemListActivity.this, promotionDetailses);
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
