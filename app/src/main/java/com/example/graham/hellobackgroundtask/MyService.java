package com.example.graham.hellobackgroundtask;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by Graham on 06/02/2017.
 */

public class MyService extends IntentService {

    public static final String TAG = ".hellobackgroundtask";
    private String urlSeachTerm;



    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        urlSeachTerm = intent.getStringExtra(MainActivity.SEARCH_URL);

        int numberOfCities = MainActivity.MAX_NEARBY_CITIES;

        NearbyCityDetails [] listOfCities = new NearbyCityDetails [numberOfCities];


        HttpHandler httpHandler = new HttpHandler();
        String data = httpHandler.makeServiceCall(urlSeachTerm);
        Log.d(TAG, data);

        if (data != null) {
            try {
                JSONArray listOfCitiesJSON = new JSONArray(data);

                for (int i = 0; i < numberOfCities; i++) {

                    //must create a new instance in the loop each time
                    //http://stackoverflow.com/questions/23338545/why-only-the-last-value-that-we-are-trying-to-add-is-stored-in-an-arraylist-repe
                    NearbyCityDetails cityDetails = new NearbyCityDetails();

                    JSONArray cityDetailsJSON = new JSONArray(listOfCitiesJSON.getString(i));

                    cityDetails.setBearing(cityDetailsJSON.getDouble(0));
                    cityDetails.setName(cityDetailsJSON.getString(1));
                    cityDetails.setDistanceNauticalMiles(cityDetailsJSON.getDouble(5));
                    cityDetails.setLatitude(cityDetailsJSON.getDouble(8));
                    cityDetails.setLongitude(cityDetailsJSON.getDouble(10));

                    listOfCities[i] = cityDetails;

                    listOfCities[i].showMeData();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        listOfCities[0].showMeData();
        listOfCities[1].showMeData();
        listOfCities[2].showMeData();


        Intent sendDataBack = new Intent();
        sendDataBack.setAction(MainActivity.ResponseReceiver.BK_WORK_DONE);
        sendDataBack.putExtra("arrayOfCities" , listOfCities);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(sendDataBack);






    }

    public void sort

}