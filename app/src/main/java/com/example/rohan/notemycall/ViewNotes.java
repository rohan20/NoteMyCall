package com.example.rohan.notemycall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

public class ViewNotes extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener
{


    NoteArrayAdapter adapter;
    ListView lv;
    NotesSQLHelper helper;
    SQLiteDatabase db;
    ArrayList<Note> notes2;
    Note notePosition;
    String epochTime;
    int backButtonCount = 0;

    public String[] getAllNotes()
    {
        helper = new NotesSQLHelper(this, null, 1);
        db = helper.getReadableDatabase();

        String[] columns = {NotesSQLHelper.NOTE_TABLE_TEXT, NotesSQLHelper.NOTE_TABLE_NAME,
                NotesSQLHelper.NOTE_TABLE_NUMBER, NotesSQLHelper.NOTE_TABLE_TIME, NotesSQLHelper.NOTE_TABLE_EPOCH_TIME};
        Cursor c = db.query(NotesSQLHelper.NOTE_TABLE, columns, null, null, null, null, NotesSQLHelper.NOTE_TABLE_EPOCH_TIME + " DESC");

        int i = 0;
        String output[] = new String[5 * c.getCount()];

        while (c.moveToNext())
        {
            output[i++] = c.getString(c.getColumnIndex(NotesSQLHelper.NOTE_TABLE_TEXT));
            output[i++] = c.getString(c.getColumnIndex(NotesSQLHelper.NOTE_TABLE_NAME));
            output[i++] = c.getString(c.getColumnIndex(NotesSQLHelper.NOTE_TABLE_NUMBER));
            output[i++] = c.getString(c.getColumnIndex(NotesSQLHelper.NOTE_TABLE_TIME));
            output[i++] = c.getString(c.getColumnIndex(NotesSQLHelper.NOTE_TABLE_EPOCH_TIME));
        }

        return output;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        setTitle("All Notes");

            Snackbar.make(this.findViewById(android.R.id.content), "Click once to delete note.\nLong click to dial number.", Snackbar.LENGTH_LONG).show();

            String[] allNotes = getAllNotes();
            notes2 = new ArrayList<>();

            for(int i=0; i < allNotes.length; i+=5)
                notes2.add(new Note(allNotes[i], allNotes[i+1], allNotes[i+2], allNotes[i+3], allNotes[i+4]));

            adapter = new NoteArrayAdapter(this, 0, notes2);
            lv = (ListView)findViewById(R.id.noteListView);
            lv.setClickable(true);
            lv.setOnItemClickListener(this);
            lv.setOnItemLongClickListener(this);
            lv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if(id == R.id.action_deleteAll)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete?");
            builder.setMessage("Are you sure you want to delete all notes?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.execSQL("DELETE from " + NotesSQLHelper.NOTE_TABLE);
                    notes2.clear();
                    adapter.notifyDataSetChanged();
                    lv.setClickable(true);
                    lv.setAdapter(adapter);

                    Snackbar.make(ViewNotes.this.findViewById(android.R.id.content), "All notes deleted.", Snackbar.LENGTH_LONG).show();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
    {

        notePosition = adapter.getItem(position);
        epochTime = notePosition.noteEpochTime;

        helper = new NotesSQLHelper(this, null, 1);
        db = helper.getWritableDatabase();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete?");
        builder.setMessage("Are you sure you want to delete this note?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(view, "Note deleted.", Snackbar.LENGTH_LONG).show();
                db.delete(NotesSQLHelper.NOTE_TABLE, NotesSQLHelper.NOTE_TABLE_EPOCH_TIME + " = " + epochTime, null);
                notes2.remove(notePosition);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        Note notePosition = adapter.getItem(position);
        String callerNumber = notePosition.noteCallerNumber;

        if(callerNumber.equals("---"))
        {
            Snackbar.make(this.findViewById(android.R.id.content), "Invalid number! Cannot call.", Snackbar.LENGTH_LONG).show();
            return true;
        }
        Intent i = new Intent();
        i.setAction(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel: " + callerNumber));
        startActivity(i);
        return true;
    }


    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backButtonCount = 0;
            startActivity(intent);
        }
        else
        {
            Snackbar.make(this.findViewById(android.R.id.content), "Press the back button once again to\nclose the application.", Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
