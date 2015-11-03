package com.example.rohan.notemycall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{

    TextView tv;
    Button bYes;
    Button bNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        PhoneCallListener phoneListener = new PhoneCallListener();
//        TelephonyManager telephonyManager = (TelephonyManager) this
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(phoneListener,
//                PhoneStateListener.LISTEN_CALL_STATE);

        tv = (TextView)findViewById(R.id.doYouWantToAddANote);
        bYes = (Button)findViewById(R.id.YES);
        bNo = (Button)findViewById(R.id.NO);

        Typeface t = Typeface.createFromAsset(getAssets(), "fonts/HoboStd.ttf");
        tv.setTypeface(t);
        bYes.setTypeface(t);
        bNo.setTypeface(t);

        bNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddNote.class);
                startActivity(i);
            }
        });

    }


}
