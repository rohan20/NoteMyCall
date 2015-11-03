package com.example.rohan.notemycall;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNote extends AppCompatActivity {

    TextView tvCallerName0;
    TextView tvCallerNumber0;
    TextView tvCallTime0;
    TextView tvCallerName;
    TextView tvCallerNumber;
    TextView tvCallTime;

    Typeface t;

    EditText edAddTextHere;

    Button bSave;
    Button bCancel;
    Button bViewNotes;

    String savedNoteText;
    String name;
    String number;
    String time;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        t = Typeface.createFromAsset(getAssets(), "fonts/HoboStd.ttf");

        tvCallerName0 = (TextView)findViewById(R.id.caller0);
        tvCallerName0.setTypeface(t);
        tvCallerNumber0 = (TextView)findViewById(R.id.number0);
        tvCallerNumber0.setTypeface(t);
        tvCallTime0 = (TextView)findViewById(R.id.callerTime0);
        tvCallTime0.setTypeface(t);

        tvCallerName = (TextView)findViewById(R.id.callerName);
        tvCallerName.setTypeface(t);
        name = BroadcastMessagesReceiver.getIncomingContactName();
        if(name == null)
            name = "Unknown Caller";
        tvCallerName.setText(name);
        tvCallerName.setTextColor(Color.WHITE);


        tvCallerNumber = (TextView)findViewById(R.id.callerNumber);
        tvCallerNumber.setTypeface(t);
        number = BroadcastMessagesReceiver.getIncomingNumber();
        if(number == null)
            number = "---";
        tvCallerNumber.setText(number);
        tvCallerNumber.setTextColor(Color.WHITE);

        tvCallTime = (TextView)findViewById(R.id.callerTime);
        tvCallTime.setTypeface(t);
        time = BroadcastMessagesReceiver.getIncomingCallTime();
        if(time == null)
            time = "---";
        tvCallTime.setText(time);
        tvCallTime.setTextColor(Color.WHITE);

        edAddTextHere = (EditText)findViewById(R.id.addTextHereEditText);
        edAddTextHere.setTypeface(t);

        bSave = (Button)findViewById(R.id.saveButton);
        bSave.setTypeface(t);
        bCancel = (Button)findViewById(R.id.cancelButton);
        bCancel.setTypeface(t);
        bViewNotes = (Button)findViewById(R.id.viewNotes);
        bViewNotes.setTypeface(t);

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddNote.this, ViewNotes.class);

                savedNoteText = edAddTextHere.getText().toString();

                NotesSQLHelper helper = new NotesSQLHelper(AddNote.this, null, 1);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put("text", savedNoteText);
                cv.put("name", name);
                cv.put("number", number);
                cv.put("time", time);
                cv.put("epochTime", System.currentTimeMillis());

                db.insert(NotesSQLHelper.NOTE_TABLE, null, cv);

//                Snackbar.make(v, "Note Saved", Snackbar.LENGTH_LONG).show();

                startActivity(i);

            }
        });

        bCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNote.this, ViewNotes.class);
                startActivity(i);
            }
        });




    }
}
