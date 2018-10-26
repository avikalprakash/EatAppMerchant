package organisation.app.eat.eatapp;

import android.os.Parcel;
import android.os.Parcelable;

public class PaidItemPojo implements Parcelable{

    String p_order_id;
    String product_id;
    String qr_image;
    String user_id;
    String invoice_number;
    String user_name;
    String product_name;
    String redeem_date;
    String branch_name;

    protected PaidItemPojo(Parcel in) {
        p_order_id = in.readString();
        product_id = in.readString();
        qr_image = in.readString();
        user_id = in.readString();
        invoice_number = in.readString();
        user_name = in.readString();
        product_name = in.readString();
    }



    public PaidItemPojo(){

    }

    public static final Creator<PaidItemPojo> CREATOR = new Creator<PaidItemPojo>() {
        @Override
        public PaidItemPojo createFromParcel(Parcel in) {
            return new PaidItemPojo(in);
        }

        @Override
        public PaidItemPojo[] newArray(int size) {
            return new PaidItemPojo[size];
        }
    };

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getRedeem_date() {
        return redeem_date;
    }

    public void setRedeem_date(String redeem_date) {
        this.redeem_date = redeem_date;
    }

    public String getP_order_id() {
        return p_order_id;
    }

    public void setP_order_id(String p_order_id) {
        this.p_order_id = p_order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String promotion_id) {
        this.product_id = promotion_id;
    }

    public String getQr_image() {
        return qr_image;
    }

    public void setQr_image(String qr_image) {
        this.qr_image = qr_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(redeem_date);
        parcel.writeString(p_order_id);
        parcel.writeString(product_id);
        parcel.writeString(qr_image);
        parcel.writeString(user_id);
        parcel.writeString(invoice_number);
        parcel.writeString(user_name);
        parcel.writeString(product_name);
    }
}
