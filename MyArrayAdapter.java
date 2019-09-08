package com.example.secondassignment2;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Object> {

    Context _context;
    int _layout;
    ArrayList <User>_objects;

    public MyArrayAdapter(Context context, int layout, ArrayList objects) {
        super(context, layout, objects);
        _context = context;
        _layout = layout;
        _objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //return super.getView(position, convertView, parent);

        View result = convertView;


        if (result == null)
        {
            Log.d("=======", "Drawing " + position);
            result = LayoutInflater.from(_context).inflate(_layout,
                    parent, false);
        }

        TextView tv1 = result.findViewById(android.R.id.text1);
        tv1.setText(_objects.get(position).fullName.toString()+" ("+_objects.get(position).gender.toString()+" )");
        TextView tv2 = result.findViewById(android.R.id.text2);
        tv2.setText(_objects.get(position).exactLocation.toString());

        //final MyArrayAdapter self = this;
        //final int selfPosition = position;

        // result.setOnLongClickListener( new DeleteOnLongClickListener(this,position));


        return result;

    }
}

