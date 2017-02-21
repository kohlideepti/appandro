package com.vehicletracking.model;


import java.io.Serializable;
import java.util.List;

public class Vehicle implements Serializable{
        private String licensePlate;
        private Position currentPosition;
        private String deviceType;
        private List<Position> lastSeen;

    public Vehicle(){

    }

    public Vehicle(String licensePlate, Position currentPosition, String deviceType,List<Position> lastSeen) {
        this.licensePlate = licensePlate;
        this.currentPosition = currentPosition;
        this.deviceType = deviceType;
        this.lastSeen = lastSeen;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<Position> getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(List<Position> lastSeen) {
        this.lastSeen = lastSeen;
    }
}
