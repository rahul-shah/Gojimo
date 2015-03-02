package com.example.rahulshah.gojimotest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.rahulshah.gojimotest.Models.APIResponse;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter
{
    private Context context;
    private int layoutResourceId;
    public static boolean mIsUserLoggedIn = false;
    protected ImageLoader loader;
    private String mEventName;
    ArrayList<APIResponse> mEventsList = new ArrayList<APIResponse>();

    public CustomArrayAdapter(Context context, int layoutResourceId, ArrayList<APIResponse> eventList)
    {
        super(context, layoutResourceId,eventList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        mEventsList.addAll(eventList);
        loader = VolleySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        NetworkImageView rowImage = null;
        ImageView arrowIcon = null;
        TextView rowText = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.qualificationrow, parent, false);
        }
        rowText = (TextView)row.findViewById(R.id.txtTitle);

        rowText.setText(mEventsList.get(position).getName());
        //rowText.setTextColor(Color.parseColor(mEventsList.get(po)));
        return row;
    }
}