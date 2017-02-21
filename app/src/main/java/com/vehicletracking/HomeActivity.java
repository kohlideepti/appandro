package com.vehicletracking;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vehicletracking.gen.VehicleGenerator;
import com.vehicletracking.model.Position;
import com.vehicletracking.model.User;
import com.vehicletracking.model.Vehicle;
import com.vehicletracking.search.SearchAdapter;
import com.vehicletracking.tasks.ImageDownloader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback , GoogleMap.OnMarkerClickListener{
    private final String[] drawer_list_items = {"Sign out"};
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView mDrawerList;
    private GoogleApiClient googleApiClient;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private User user;
    private ImageView profilePicture;
    private TextView username;

    private List<Vehicle> vehicles = VehicleGenerator.getVehicles();
    private List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_view_headline_white_24dp);
    }

    private void initialize(){
        user = (User) getIntent().getSerializableExtra(User.INTENT_NAME);
        user.setProfilePicture("https://assets.entrepreneur.com/content/16x9/822/20150406145944-dos-donts-taking-perfect-linkedin-profile-picture-selfie-mobile-camera-2.jpeg");

        mToolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(drawerToggle);

        mDrawerList =(ListView) findViewById(R.id.drawer_list);
        ArrayAdapter<String> drawerListAdapter = new ArrayAdapter<String>(this,R.layout.custom_list_textview,drawer_list_items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(getResources().getColor(R.color.darkest_gray));
                return view;
            }
        };

        mDrawerList.setAdapter(drawerListAdapter);
        setDrawerListListener();

        profilePicture = (ImageView) findViewById(R.id.profile_picture);
        username = (TextView) findViewById(R.id.username);

        new ImageDownloader(profilePicture).execute(user.getProfilePicture());
        username.setText(user.getUsername());


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

         googleApiClient   = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


         mapFragment = new SupportMapFragment();
         getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,mapFragment).commit();
         mapFragment.getMapAsync(this);

    }

    private void setDrawerListListener(){
          mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  switch (position){
                      case 0:signOut();
                            break;
                  }
              }
          });
    }


    private void signOut(){
        final Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(user.getLoginMethod().equals(User.LOGIN_METHOD_GOOGLE)){
            if(googleApiClient.isConnected()){
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()){
                            Toast.makeText(getApplicationContext(),"You have successfully logged out",Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
            else {
                Toast.makeText(getApplicationContext(),"Sorry gac not connected",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"You have successfully logged out",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle != null && drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null)
            drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null)
            drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),connectionResult.getErrorCode(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map  = googleMap;
        googleMap.setOnMarkerClickListener(this);
        for(Vehicle vehicle:vehicles){
            Position vehiclePos = vehicle.getCurrentPosition();
            LatLng vehiclePosition = new LatLng(vehiclePos.getLatitude(),vehiclePos.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(vehiclePosition).title("Vehicle Type : " + vehicle.getDeviceType()));
            markers.add(marker);
            map.moveCamera(CameraUpdateFactory.newLatLng(vehiclePosition));
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int markerPosition  =  markers.indexOf(marker);

        Vehicle vehicle = vehicles.get(markerPosition);
        VehicleInfoDialog dialog = VehicleInfoDialog.newInstance(vehicle);
        dialog.show(getSupportFragmentManager(),"Vehicle Info "+vehicle.getDeviceType());
        return true;
    }

    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);


        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FragmentTransaction  transaction = getSupportFragmentManager().beginTransaction();
                 transaction.replace(R.id.fragment_container,SearchFragment.newInstance());
                 transaction.addToBackStack(null);
                 transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                 transaction.commit();

            }
        });




        return true;
    }


    public void onBackPressed() {
        finish();
    }

}




