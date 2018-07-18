package com.example.aakas.todo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1;
        String msgBody="";
        String[] part= new String[10];
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage [] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }

            part = msgBody.split("(?<=\\D)(?=\\d)");

        }



        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("channelid","cname",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"channelid");

        builder.setContentTitle("SMS Notification");
        builder.setContentText("Click to add in TODO");
        builder.setSmallIcon(R.drawable.ic_launcher_background);

        intent1= new Intent(context,Main2Activity.class);
        intent1.putExtra("title", part[0]);
        intent1.putExtra("amount",part[1]);
        intent1.putExtra("check",1);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,1,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification= builder.build();
        manager.notify(1,notification);






    }
}

