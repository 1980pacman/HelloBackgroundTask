package com.example.graham.hellobackgroundtask;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Graham on 06/02/2017.
 */

public class NearbyCityDetails implements Serializable {
    private double latitude, longitude;
    private double bearing, distanceNauticalMiles;
    private String placeName;


    public void NearbyCityDetails(){

    }


    public void setName(String placeName){
        this.placeName = placeName;
    }

    public String getName(){
        return placeName;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setBearing(double bearing){
        this.bearing = bearing;
    }

    public double getBearing(){
        return bearing;
    }

    public void setDistanceNauticalMiles(double distanceNauticalMiles ){
        this.distanceNauticalMiles = distanceNauticalMiles;

    }

    public double getDistanceNauticalMiles(){
        return distanceNauticalMiles;
    }

    public void showMeData(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nCity: ").append(placeName).append("\n");
        sb.append("Latitude: ").append(Double.toString(latitude)).append("\n");
        sb.append("Longitude: ").append(Double.toString(longitude)).append("\n");
        sb.append("Bearing: ").append(Double.toString(bearing)).append("\n");
        sb.append("Distance: ").append(Double.toString(distanceNauticalMiles)).append("\n");
        Log.d(MainActivity.TAG, sb.toString());
    }

}
