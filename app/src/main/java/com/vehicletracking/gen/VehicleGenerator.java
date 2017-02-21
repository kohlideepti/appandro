package com.vehicletracking.gen;

import com.vehicletracking.model.Position;
import com.vehicletracking.model.Vehicle;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2/21/2017.
 */

public class VehicleGenerator {


    public static List<Vehicle> getVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();
        List<Position> lastSeen = new ArrayList<>();
        lastSeen.add(new Position(12.9716f,77.5946f));
        lastSeen.add(new Position(28.7041f,77.1025f));
        lastSeen.add(new Position(13.0827f,80.2707f));
        lastSeen.add(new Position(19.7515f,75.7139f));
        lastSeen.add(new Position(10.8505f,76.2711f));
        lastSeen.add(new Position(12.2958f, 76.6394f));
        lastSeen.add(new Position(15.3647f,75.1240f));


        vehicles.add(new Vehicle("KA 14 1998",new Position(12,71),"Etios",lastSeen));
        vehicles.add(new Vehicle("KA 15 1996",new Position(15,75),"ford figo",lastSeen));
        vehicles.add(new Vehicle("KA 14 1994",new Position(14,77),"XUV 500",lastSeen));
        vehicles.add(new Vehicle("KA 14 1784",new Position(13,70),"elantra",lastSeen));
        vehicles.add(new Vehicle("KA 17 5405",new Position(20,74),"Tata Indica",lastSeen));
        return vehicles;
    }

}
