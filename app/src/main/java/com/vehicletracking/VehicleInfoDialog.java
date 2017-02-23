package com.vehicletracking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vehicletracking.model.Position;
import com.vehicletracking.model.Vehicle;

import java.util.List;

/**
 * Created by admin on 2/21/2017.
 */

public class VehicleInfoDialog extends DialogFragment implements OnMapReadyCallback{
    private Vehicle vehicle;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private View view;


    public static VehicleInfoDialog newInstance(Vehicle vehicle){
        VehicleInfoDialog info = new VehicleInfoDialog();
        Bundle args = new Bundle();
        args.putSerializable("Vehicle",vehicle);
        info.setArguments(args);
        return info;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.dialog_vehicle_info,container,false);
        } catch (InflateException e) {
            Log.d("Error","Inflate Exception",e);
        }
        Bundle args = getArguments();
        vehicle = (Vehicle) args.getSerializable("Vehicle");


        TextView vehicleType = (TextView) view.findViewById(R.id.vehicle_type);
        vehicleType.setText(vehicle.getDeviceType());

        TextView licensePlate = (TextView) view.findViewById(R.id.vehicle__license_plate);
        licensePlate.setText(vehicle.getLicensePlate());

        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if(mapFragment == null){
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.vehicle_last_seen_container, mapFragment, "mapFragment");
            ft.commit();

        }


        mapFragment.getMapAsync(this);



        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        List<Position> vehiclePosition = vehicle.getLastSeen();
        for(Position position : vehiclePosition){
            LatLng pos = new LatLng(position.getLatitude(),position.getLongitude());
            map.addMarker(new MarkerOptions().position(pos).title("Last Seen"));
            map.moveCamera(CameraUpdateFactory.newLatLng(pos));

        }

    }
}
