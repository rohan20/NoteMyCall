package com.example.rohan.notemycall;

/**
 * Created by Rohan on 21-Oct-15.
 */
public class Note
{
    String noteText;
    String noteCallerName;
    String noteCallerNumber;
    String noteCallTime;
    String noteEpochTime;

    public Note(String noteText, String noteCallerName, String noteCallerNumber, String noteCallTime, String noteEpochTime)
    {
        this.noteText = noteText;
        this.noteCallerName = noteCallerName;
        this.noteCallerNumber = noteCallerNumber;
        this.noteCallTime = noteCallTime;
        this.noteEpochTime = noteEpochTime;
    }
}
