package organisation.app.eat.eatapp.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import organisation.app.eat.eatapp.PaymentNotificationDetails;
import organisation.app.eat.eatapp.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    NotificationManager notificationManager;
    Intent intent;
    String type;
    String transaction_id;
    String user_mail;
    String user_name;
    String mobile;
    String amount;
    String merchant_id;
    String merchant_name;
    String qr_image;
    String invoice;
    String title;






    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> params = remoteMessage.getData();
        Log.d(TAG, "Notification" + params);
        JSONObject jsonObject = new JSONObject(params);
        sendNotification(remoteMessage.getData());
        try {
            String s= jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void soundNotification(String msg) {

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.eatapp)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setSmallIcon(R.drawable.rrrrrrrrr);
//        } else {
//            notificationBuilder.setSmallIcon(R.mipmap.ic_dainik);
//        }

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[0]);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void soundNotificationImage(String msg, String url) {

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(getString(R.string.app_name));
        bigPictureStyle.setSummaryText(Html.fromHtml(msg).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
        try {
            notificationBuilder
                    .setSmallIcon(R.drawable.eatapp)
                    .setLargeIcon(Picasso.with(getApplicationContext()).load(url).get())
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(msg)
                    .setStyle(bigPictureStyle)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[0]);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void sendNotification(Map<String, String> params) {

        Log.d(TAG, "Notification Message Body1: " + params);
        JSONObject jsonObject = new JSONObject(params);

        try {
            type = jsonObject.getString("type");
            if (type.equals("9")) {
                merchant_name = jsonObject.getString("merchant_name");
                transaction_id = jsonObject.getString("transaction_id");
                user_mail = jsonObject.getString("user_mail");
                user_name = jsonObject.getString("user_name");
                mobile  = jsonObject.getString("mobile");
                amount = jsonObject.getString("amount");
                merchant_id = jsonObject.getString("merchant_id");
                qr_image = jsonObject.getString("qr_image");
                invoice = jsonObject.getString("invoice");
                title = jsonObject.getString("title");
            }else if (type.equals("10")){
                title = jsonObject.getString("title");
                merchant_name = jsonObject.getString("merchant_name");
                transaction_id = jsonObject.getString("transaction_id");
                user_mail = jsonObject.getString("user_mail");
                user_name = jsonObject.getString("user_name");
                mobile  = jsonObject.getString("mobile");
                amount = jsonObject.getString("amount");
                merchant_id = jsonObject.getString("merchant_id");
                qr_image = jsonObject.getString("qr_image");
                invoice = jsonObject.getString("invoice");

            }else if (type.equals("11")){
                merchant_name = jsonObject.getString("merchant_name");
                transaction_id = jsonObject.getString("transaction_id");
                user_mail = jsonObject.getString("user_mail");
                user_name = jsonObject.getString("user_name");
                mobile  = jsonObject.getString("mobile");
                amount = jsonObject.getString("amount");
                merchant_id = jsonObject.getString("merchant_id");
                qr_image = jsonObject.getString("qr_image");
                invoice = jsonObject.getString("invoice");
                title = jsonObject.getString("title");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (type.equals("9")) {
            Intent intent = new Intent(getApplicationContext(), PaymentNotificationDetails.class);
            intent.putExtra("type", type);
            intent.putExtra("merchant_name", merchant_name);
            intent.putExtra("transaction_id", transaction_id);
            intent.putExtra("user_mail", user_mail);
            intent.putExtra("user_name", user_name);
            intent.putExtra("mobile", mobile);
            intent.putExtra("amount", amount);
            intent.putExtra("merchant_id", merchant_id);
            intent.putExtra("qr_image", qr_image);
            intent.putExtra("invoice", invoice);
            intent.putExtra("title", title);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long[] v = {500, 1000};
            Notification.Builder notificationBuilder = new Notification.Builder(this)

                    .setSmallIcon(R.drawable.eat).setContentTitle("Payment").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setVibrate(v).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, notificationBuilder.build());
        }else if (type.equals("10")) {

            Intent intent = new Intent(getApplicationContext(), PaymentNotificationDetails.class);
            intent.putExtra("type", type);
            intent.putExtra("merchant_name", merchant_name);
            intent.putExtra("transaction_id", transaction_id);
            intent.putExtra("user_mail", user_mail);
            intent.putExtra("user_name", user_name);
            intent.putExtra("mobile", mobile);
            intent.putExtra("amount", amount);
            intent.putExtra("merchant_id", merchant_id);
            intent.putExtra("qr_image", qr_image);
            intent.putExtra("invoice", invoice);
            intent.putExtra("title", title);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long[] v = {500, 1000};
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.eat).setContentTitle("Payment").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setVibrate(v).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, notificationBuilder.build());
        }else if (type.equals("11")) {

            Intent intent = new Intent(getApplicationContext(), PaymentNotificationDetails.class);
            intent.putExtra("type", type);
            intent.putExtra("merchant_name", merchant_name);
            intent.putExtra("transaction_id", transaction_id);
            intent.putExtra("user_mail", user_mail);
            intent.putExtra("user_name", user_name);
            intent.putExtra("mobile", mobile);
            intent.putExtra("amount", amount);
            intent.putExtra("merchant_id", merchant_id);
            intent.putExtra("qr_image", qr_image);
            intent.putExtra("invoice", invoice);
            intent.putExtra("title", title);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            long[] v = {500, 1000};
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)

                    .setSmallIcon(R.drawable.eat).setContentTitle("Payment").setContentText(title).setAutoCancel(true).setSound(defaultSoundUri).setVibrate(v).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("notify_001",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
