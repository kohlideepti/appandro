package com.vehicletracking.model;

import java.io.Serializable;

/**
 * Created by admin on 2/21/2017.
 */

public class Position implements Serializable{
  private float latitude;
  private float longitude;

    public Position(){

    }

    public Position(float langitude,float longitude){
        this.latitude = langitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }
}
