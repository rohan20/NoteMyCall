package com.example.rohan.notemycall;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telecom.Call;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.transform.URIResolver;

/**
 * Created by Rohan on 17-Oct-15.
 */


public class BroadcastMessagesReceiver extends BroadcastReceiver
{

    public static String incomingNumber;
    public static String incomingContactName;
    public static String incomingCallTime;


    public static String getIncomingNumber()
    {
        return incomingNumber;
    }

    public static String getIncomingContactName()
    {
        return incomingContactName;
    }

    public static String getIncomingCallTime()
    {
        return incomingCallTime;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
       if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)
           || intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
       {

           //get Date and Time
           Date d = new Date();
           incomingCallTime = d.toString();

           //get Incoming  Number
           incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

           //get Contact Name
           String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME};
           Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(incomingNumber));
           Cursor c = context.getContentResolver().query(uri, projection, null, null, null);

           while (c.moveToNext())
           {
               incomingContactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
           }

           c.close();

           Intent i = new Intent(context, MainActivity.class);
           i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(i);
       }
    }
}

