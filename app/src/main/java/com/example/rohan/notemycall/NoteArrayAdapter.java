package com.example.rohan.notemycall;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Rohan on 21-Oct-15.
 */
public class NoteArrayAdapter extends ArrayAdapter<Note>
{

    Context context;

    public NoteArrayAdapter(Context context, int resource, List<Note> objects)
    {
        super(context, resource, objects);
        this.context = context;
    }

    public static class NoteViewHolder
    {
        TextView noteText;
        TextView name;
        TextView number;
        TextView time;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = View.inflate(context, R.layout.note_layout, null);

            NoteViewHolder vh = new NoteViewHolder();
            vh.noteText = (TextView)convertView.findViewById(R.id.noteText);
            vh.name = (TextView)convertView.findViewById(R.id.noteCallerName);
            vh.number = (TextView)convertView.findViewById(R.id.noteCallerNumber);
            vh.time = (TextView)convertView.findViewById(R.id.noteCallerTime);

            convertView.setTag(vh);
        }

        Note notePosition = getItem(position);
        NoteViewHolder vh = (NoteViewHolder)convertView.getTag();

        vh.noteText.setText(notePosition.noteText);
        vh.name.setText(notePosition.noteCallerName);
        vh.number.setText(notePosition.noteCallerNumber);
        vh.time.setText(notePosition.noteCallTime);

        return convertView;
    }

}
