package com.example.rahulshah.gojimotest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.rahulshah.gojimotest.Models.APIResponse;
import com.example.rahulshah.gojimotest.Models.Subjects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
{

    private StringRequest mJsonReq;
    private RequestQueue mQueue;
    private String mUri;
    private ListView mListOfQuals;
    private ArrayList<APIResponse> mAPIResponseList;
    public static Cache.Entry mEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListOfQuals = (ListView) findViewById(R.id.listofqualifications);

        final ProgressDialog progressDialog = getLoadingDialog(this);
        progressDialog.show();

        setClickListeners();

        mUri = "https://api.gojimo.net/api/v4/qualifications";

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();

        mEntry = mQueue.getCache().get("https://api.gojimo.net/api/v4/qualifications");

        mJsonReq = new StringRequest(mUri,new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                mEntry = new Cache.Entry();
                mEntry.data = response.toString().getBytes();
                mQueue.getCache().put("https://api.gojimo.net/api/v4/qualifications",mEntry);

                if (progressDialog.isShowing()) progressDialog.dismiss();
                mAPIResponseList = new ArrayList<APIResponse>();
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                JsonParser parser = new JsonParser();

                JsonArray eventArray = parser.parse(response.toString()).getAsJsonArray();

                for (JsonElement obj : eventArray)
                {
                    APIResponse offerFromAPI = gson.fromJson(obj, APIResponse.class);
                    mAPIResponseList.add(offerFromAPI);
                }

                final CustomArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mAPIResponseList);
                mListOfQuals.setAdapter(adapter);
            }
        },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        mJsonReq.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(mEntry == null)
        {
            mJsonReq.setShouldCache(true);
            mQueue.add(mJsonReq);
        }
        //Load from cache but also look for new events
        else
        {
            String responseCacheString = new String(mEntry.data);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            JsonParser parser = new JsonParser();

            if (progressDialog.isShowing()) progressDialog.dismiss();
            mAPIResponseList = new ArrayList<APIResponse>();

            JsonArray eventArray = parser.parse(responseCacheString).getAsJsonArray();

            for (JsonElement obj : eventArray)
            {
                APIResponse offerFromAPI = gson.fromJson(obj, APIResponse.class);
                mAPIResponseList.add(offerFromAPI);
            }

            final CustomArrayAdapter adapter = new CustomArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mAPIResponseList);
            mListOfQuals.setAdapter(adapter);
       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static ProgressDialog getLoadingDialog(final Context context)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public void setClickListeners()
    {
        mListOfQuals.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<Subjects> subjectList = mAPIResponseList.get(position).getsubjects();
                if(subjectList.size() > 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("List of subjects");

                    ListView modeList = new ListView(MainActivity.this);


                    SubjectListAdapter modeAdapter = new SubjectListAdapter(MainActivity.this, android.R.layout.simple_list_item_1,subjectList);
                    modeList.setAdapter(modeAdapter);

                    builder.setView(modeList);
                    final Dialog dialog = builder.create();

                    dialog.show();
                }
                else
                {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error")
                            .setMessage("Sorry no subjects found for this qualification")
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
                                        }
                                    })
                            .setCancelable(false)
                            .show();
                }
            }
        });
    }
}
