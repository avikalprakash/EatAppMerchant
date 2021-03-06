package organisation.app.eat.eatapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GiftListAdapter extends BaseAdapter{

    ArrayList<GiftPojo> introducerPojoArrayList =new  ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    ImageView imageView;
    TextView introducer_name, amount, user_name, date;



    public GiftListAdapter(FragmentActivity activity, ArrayList<GiftPojo> introducerPojoArrayList){
        this.introducerPojoArrayList=introducerPojoArrayList;
        this.context=activity;
    }
    @Override
    public int getCount() {
        return introducerPojoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return introducerPojoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
         view = layoutInflater.inflate(R.layout.payment_adapter_layout, null);
        introducer_name = (TextView)view.findViewById(R.id.introducer_name);
        amount = (TextView)view.findViewById(R.id.phone_no);
        user_name = (TextView)view.findViewById(R.id.user_name);
        date = (TextView)view.findViewById(R.id.date);
        imageView = (ImageView)view.findViewById(R.id.image_qr);

        introducer_name.setText(introducerPojoArrayList.get(i).getGift_name());
        amount.setText(introducerPojoArrayList.get(i).getPoints()+" Points");
        user_name.setText(introducerPojoArrayList.get(i).getOrder_invoice());
        date.setText(introducerPojoArrayList.get(i).getBuy_date());
        Picasso.with(context).load(introducerPojoArrayList.get(i).getQr_image()).into(imageView);

        return view;
    }
}
