package com.example.graham.hellobackgroundtask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String SEARCH_URL = "Key1";
    public static final String MAX_CITIES = "Key2";
    private ResponseReceiver receiver;

    public static final String TAG = ".hellobackgroundtask";

    private static String baseUrl = "http://getnearbycities.geobytes.com/GetNearbyCities?";
    private String completedUrl;
    public int minRadius = 2;
    public int radius = 500;
    public static final int MAX_NEARBY_CITIES = 3;
    public static final int ANGLE_SEGMENT = 6; // Must be a factor of 360
    public double latitude = 55.9592450;
    public double longitude = -3.1791630;


    public NearbyCityDetails [] listOfCityBearings = new NearbyCityDetails [MAX_NEARBY_CITIES];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createQueryURL();

        Intent goDoStuff = new Intent(this, MyService.class);
        goDoStuff.putExtra(SEARCH_URL, completedUrl);
        startService(goDoStuff);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter broadcastFilter = new IntentFilter(ResponseReceiver.BK_WORK_DONE);
        receiver = new ResponseReceiver();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver, broadcastFilter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager localBroadcastManager =
                LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }

    public void createQueryURL(){
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append("&Latitude=").append(latitude);
        sb.append("&Longitude=").append(longitude);
        sb.append("&radius=").append(radius);
        sb.append("&minradius=").append(minRadius);
        sb.append("&limit=").append(MAX_NEARBY_CITIES-1);
        completedUrl = sb.toString();

    }

    public class ResponseReceiver extends BroadcastReceiver{

        public static final String BK_WORK_DONE = "com.example.graham.hellobackgroundtask.BK_WORK_DONE";

        @Override
        public void onReceive(Context context, Intent intent) {
            listOfCityBearings = (NearbyCityDetails[]) intent.getSerializableExtra("arrayOfCities");

            String [] listOfCityNames = new String[MAX_NEARBY_CITIES];

            for(int i =  0; i < listOfCityBearings.length; i++){
                listOfCityNames[i] = listOfCityBearings[i].getName();
            }

            Toast.makeText(context, listOfCityNames[0], Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
            adapter.addAll(listOfCityNames);
            ListView listview = (ListView) findViewById(R.id.listOfCities);
            listview.setAdapter(adapter);



        }
    }



}




