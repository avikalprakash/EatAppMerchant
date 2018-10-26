package organisation.app.eat.eatapp;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by lue on 23-06-2017.
 */

public class SearchListAdaptor extends BaseAdapter {

    ArrayList<SearchPojo> promotionDetailses=new ArrayList<>();
     TextView mUsername;
    TextView mEmail;
    TextView mMobile;
    TextView mGender;
    ImageView image;
    Button add;
    String ReciverMobile="";
    private static final int SHOW_PROCESS_DIALOG = 1;
    private static final int HIDE_PROCESS_DIALOG = 0;
    LayoutInflater mInflater;
    private Activity context;
    public SearchListAdaptor(FragmentActivity activity, ArrayList<SearchPojo> storeContacts) {
        this.context=activity;
        this.promotionDetailses=storeContacts;
    }
    @Override
    public int getCount() {
        return promotionDetailses.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.search_list_adapter, null);
        mUsername = (TextView) view.findViewById(R.id.username);
       /* mEmail = (TextView) view.findViewById(R.id.email);
        mMobile = (TextView) view.findViewById(R.id.mobile);
        mGender = (TextView) view.findViewById(R.id.gender);*/
        image = (ImageView)view.findViewById(R.id.image_item);
        if(promotionDetailses.get(i).getUser_name()!=null){
            mUsername.setText(promotionDetailses.get(i).getUser_name());
        }else {
            mUsername.setText(" ");
        }
       /* if(promotionDetailses.get(i).getEmail()!=null){
            mEmail.setText("Email : "+promotionDetailses.get(i).getEmail());
        }else {
            mEmail.setText(" ");
        }

        if(promotionDetailses.get(i).getMobile()!=null){
            mMobile.setText("Mobile : "+promotionDetailses.get(i).getMobile());
        }else {
            mMobile.setText(" ");
        }
        if(promotionDetailses.get(i).getGender()!=null){
            mGender.setText("Gender : "+promotionDetailses.get(i).getGender());
        }else {
            mGender.setText(" ");
        }*/
        if (promotionDetailses.get(i).getPhoto()!=null){
            Picasso.with(context).load(promotionDetailses.get(i).getPhoto()).into(image);
        }
        return view;
    }
}
