package com.vehicletracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vehicletracking.model.Position;
import com.vehicletracking.model.Vehicle;

public class SingleVehicleActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private Vehicle vehicle;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signle_vehicle);

        initialize();
        setSupportActionBar(mToolbar);

    }

    private void initialize(){
        mToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        mToolbar.setTitle("Single Vehicle View");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        Bundle bundle =  getIntent().getBundleExtra("Bundle");
        vehicle = (Vehicle) bundle.getSerializable("Vehicle");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        Position position = vehicle.getCurrentPosition();
        LatLng latLng = new LatLng(position.getLatitude(),position.getLongitude());
        Marker marker = map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.setOnMarkerClickListener(this);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        VehicleInfoDialog dialog = VehicleInfoDialog.newInstance(vehicle);
        dialog.show(getSupportFragmentManager(),"Single Vehicle Dialog");
        return true;
    }
}
