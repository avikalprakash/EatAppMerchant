package organisation.app.eat.eatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentNotificationDetails extends AppCompatActivity {
   TextView receipt_text, outlet, amount_text;
   Button ok;
   ImageView approved;
   String merchant_name, receipt_no, amount, type, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification_details);
        receipt_text = (TextView)findViewById(R.id.payment_text);
        outlet = (TextView)findViewById(R.id.outlet_text);
        amount_text=(TextView)findViewById(R.id.amount_text);
        approved = (ImageView)findViewById(R.id.approved);
        ok = (Button) findViewById(R.id.ok);
        try {
            merchant_name = getIntent().getStringExtra("merchant_name");
        }catch (Exception e){}
        try {
            receipt_no = getIntent().getStringExtra("invoice");
        }catch (Exception e){}
        try {
            amount = getIntent().getStringExtra("amount");
        }catch (Exception e){}
        try {
            type = getIntent().getStringExtra("type");
        }catch (Exception e){}
        try{
            title = getIntent().getStringExtra("title");
        }catch (Exception e){}
        if (type.equals("9")){
            receipt_text.setText("Payment with receipt no. : "+receipt_no+ " Successful");
            outlet.setText("Merchant Name : "+merchant_name);
            amount_text.setText("Amount : "+amount+" RM");
        }else if (type.equals("10")){
            receipt_text.setVisibility(View.INVISIBLE);
            amount_text.setVisibility(View.INVISIBLE);
            approved.setImageResource(R.drawable.sad);
            outlet.setText(title);
        }else if (type.equals("11")){
            receipt_text.setVisibility(View.INVISIBLE);
            amount_text.setVisibility(View.INVISIBLE);
            approved.setImageResource(R.drawable.sad);
            outlet.setText(title);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
