package organisation.app.eat.eatapp;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CouponQRListAdaptor extends BaseAdapter {
    LayoutInflater inflater;
    private Activity context;
    LayoutInflater mInflater;
//    @BindView(R.id.user_name)
    TextView tvUsername;
 //   @BindView(R.id.invoice_no)
    TextView tvInvoiceID;
 //   @BindView(R.id.product_name)
    TextView tvProductName;
 //   @BindView(R.id.redeem_date)
    TextView tvRedeemDate;
 //   @BindView(R.id.image)
    ImageView imageView;
    ArrayList<PaidItemPojo> categoryPojoArrayList=new ArrayList<>();
    public CouponQRListAdaptor(FragmentActivity activity, ArrayList<PaidItemPojo> storeContacts){
        this.context=activity;
        this.categoryPojoArrayList=storeContacts;
    }
    @Override
    public int getCount() {
        return categoryPojoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryPojoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //   View v=inflater.inflate(R.layout.transactionsinglerow,null);
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.paid_item_list, null);
        tvUsername = (TextView)view.findViewById(R.id.user_name);
        tvProductName = (TextView)view.findViewById(R.id.product_name);
        tvInvoiceID = (TextView)view.findViewById(R.id.invoice_no);
        tvRedeemDate = (TextView)view.findViewById(R.id.redeem_date);
        imageView= (ImageView)view.findViewById(R.id.image);
        tvProductName.setText(categoryPojoArrayList.get(i).getProduct_name());
        tvUsername.setText(categoryPojoArrayList.get(i).getUser_name());
        tvInvoiceID.setText(categoryPojoArrayList.get(i).getBranch_name());
        tvRedeemDate.setText(categoryPojoArrayList.get(i).getRedeem_date());
        Picasso.with(context).load(categoryPojoArrayList.get(i).getQr_image()).into(imageView);
        return view;
    }
}
