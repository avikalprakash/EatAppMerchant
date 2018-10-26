package organisation.app.eat.eatapp;

public class PaymentPojo {
  String branch_name;
  String user_name;
  String amount;
  String date;
  String qr_image;

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQr_image() {
        return qr_image;
    }

    public void setQr_image(String qr_image) {
        this.qr_image = qr_image;
    }
}
