package com.example.rohan.notemycall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rohan on 21-Oct-15.
 */
public class NotesSQLHelper extends SQLiteOpenHelper
{

    public static final String NOTE_TABLE = "Notes";
    public static final String NOTE_TABLE_TEXT = "Text";
    public static final String NOTE_TABLE_NAME = "Name";
    public static final String NOTE_TABLE_NUMBER = "Number";
    public static final String NOTE_TABLE_TIME = "Time";
    public static final String NOTE_TABLE_EPOCH_TIME = "EpochTime";

    public NotesSQLHelper(Context context, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, "All Notes", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + NOTE_TABLE + " ( " + NOTE_TABLE_TEXT + " varchar(255), " +
                NOTE_TABLE_NAME + " varchar(255), " + NOTE_TABLE_NUMBER + " varchar(255), " + NOTE_TABLE_TIME + " varchar(255), "
                    + NOTE_TABLE_EPOCH_TIME + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
