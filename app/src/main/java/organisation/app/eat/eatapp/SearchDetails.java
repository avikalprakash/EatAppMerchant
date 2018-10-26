package organisation.app.eat.eatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SearchDetails extends AppCompatActivity {
     ImageView back;
     Button ok;
    String id="";
    String user_name="";
    String mobile="";
    String email="";
    String gender="";
    String photo="";
    TextView usernameText, mobileText, emailText, genderText;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        back=(ImageView)findViewById(R.id.back);
        ok=(Button)findViewById(R.id.ok);
        id=getIntent().getStringExtra("id");
        user_name=getIntent().getStringExtra("user_name");
        mobile=getIntent().getStringExtra("mobile");
        email=getIntent().getStringExtra("email");
        gender=getIntent().getStringExtra("gender");
        photo=getIntent().getStringExtra("photo");
        usernameText=(TextView)findViewById(R.id.username);
        mobileText=(TextView)findViewById(R.id.mobile);
        emailText=(TextView)findViewById(R.id.email);
        genderText=(TextView)findViewById(R.id.gender);
        imageView=(ImageView) findViewById(R.id.image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Picasso.with(getApplicationContext()).load(photo)
                //    .resize(120, 60)
                .into(imageView);


        String sourceString = "<b>" + "Name : " + "</b> " + user_name;
        usernameText.setText(Html.fromHtml(sourceString));

        String sourceString2 = "<b>" + "Mobile : " + "</b> " + mobile;
        mobileText.setText(Html.fromHtml(sourceString2));

        String sourceString3 = "<b>" + "Email : " + "</b> " + email;
        emailText.setText(Html.fromHtml(sourceString3));

        String sourceString4 = "<b>" + "Gender : " + "</b> " + gender;
        genderText.setText(Html.fromHtml(sourceString4));
    }
}
