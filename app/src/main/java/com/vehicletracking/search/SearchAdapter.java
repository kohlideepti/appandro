package com.vehicletracking.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.vehicletracking.HomeActivity;
import com.vehicletracking.R;
import com.vehicletracking.SearchFragment;
import com.vehicletracking.SingleVehicleActivity;
import com.vehicletracking.gen.VehicleGenerator;
import com.vehicletracking.model.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 2/21/2017.
 */

public class SearchAdapter extends ArrayAdapter<Vehicle>{
   private List<Vehicle> originalList = VehicleGenerator.getVehicles();
   private List<Vehicle> searchList ;
   private HomeActivity activity;
   private Context context;

    public SearchAdapter(Context context, int resource, List<Vehicle> searchList) {
        super(context, resource, searchList);
        this.activity = (HomeActivity) context;
        this.searchList  = searchList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.searchable_listview,parent,false);
        final Vehicle vehicle = searchList.get(position);

        TextView deviceType = (TextView) view.findViewById(R.id.device_type);
        deviceType.setText(vehicle.getDeviceType());

        TextView deviceLocation = (TextView) view.findViewById(R.id.device_location);
        deviceLocation.setText("Banglore");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity activity  = (HomeActivity) context;
                SupportMapFragment mapFragment = new SupportMapFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mapFragment).commit();
                mapFragment.getMapAsync(activity);

                Intent intent = new Intent(context,SingleVehicleActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Vehicle",vehicle);

                intent.putExtra("Bundle",bundle);

                context.startActivity(intent);
            }
        });
        return view;
    }

    public void filter(String searchText){
        searchText = searchText.toLowerCase();
        searchList.removeAll(searchList);
        if(searchText.length() == 0){
            originalList.addAll(searchList);
        }
        else{
            for(Vehicle vehicle : originalList){
                String vehicleType = vehicle.getDeviceType().toLowerCase();
                if(vehicleType.contains(searchText)){
                     searchList.add(vehicle);
                }
            }

            notifyDataSetChanged();
        }

    }





}
