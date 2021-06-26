package com.example.shopapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final LatLng USKUDAR = new LatLng(41.026645, 29.014984);
    private final LatLng KADIKOY = new LatLng(40.992574, 29.023860);
    ArrayList<Location> locations = new ArrayList<Location>();
    public class Location{
        private String Title;
        private Double Latitude;
        private Double Longitude;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public Double getLatitude() {
            return Latitude;
        }

        public void setLatitude(Double latitude) {
            Latitude = latitude;
        }

        public Double getLongitude() {
            return Longitude;
        }

        public void setLongitude(Double longitude) {
            Longitude = longitude;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ArrayList<Basket> baskets = new ArrayList<Basket>();
        SqlLiteHelperBasket sqlLiteHelperBasket = new SqlLiteHelperBasket(this);
        baskets = sqlLiteHelperBasket.GetBaskets();
        Log.i("BASKETSIZE: ", String.valueOf(baskets.size()));
        for(int i = 0; i < baskets.size(); i++){
            Log.i("I: ", String.valueOf(i));
            String loc = baskets.get(i).getOrderLoc();
            String[] locs = loc.split(",");
            Double locX = Double.parseDouble(locs[0]);
            Double locY = Double.parseDouble(locs[1]);
            Location location = new Location();
            location.setTitle(baskets.get(i).getName());
            location.setLatitude(locX);
            location.setLongitude(locY);
            locations.add(location);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
         */

        try {
            if(mMap != null){
                for(int i = 0; i < locations.size(); i++){
                    mMap.addMarker(new MarkerOptions().position(new LatLng(locations.get(i).getLatitude(), locations.get(i).getLongitude()))
                            .title(locations.get(i).getTitle()));
                }
            /*
            mMap.addMarker(new MarkerOptions().position(USKUDAR)
            .title("USKUDAR"));
            mMap.addMarker(new MarkerOptions().position(KADIKOY)
                    .title("KADIKOY"));
            mMap.addMarker(new MarkerOptions().position(KADIKOY)
                    .title("KADIKOY2"));
             */
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KADIKOY, 13));
        } catch (Exception ex){
            Log.i("ONMAPREADY!", ex.toString());
        }
    }
}