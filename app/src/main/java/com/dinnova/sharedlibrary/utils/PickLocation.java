package com.dinnova.sharedlibrary.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.dinnova.sharedlibrary.ui.CustomMapTheme;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Snosey on 3/4/2018.
 */

public class PickLocation {
    public LatLng currentMaplatLng;
    List<Address> addresses;
    LocationRequest mLocationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    Location mLastLocation;
    private Geocoder geocoder;
    private Marker mCurrLocationMarker;
    private LatLng latLng;
    FragmentActivity fragmentActivity;
    GoogleMap mGoogleMap;
    LocationCallback mLocationCallback;
    private boolean showMarker;
    private Response.Listener<LatLng> listener;


    public void setLocationInMap(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        if (showMarker)
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        currentMaplatLng = latLng;

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {
                    addresses = geocoder.getFromLocation(mGoogleMap.getCameraPosition().target.latitude, mGoogleMap.getCameraPosition().target.longitude, 1);
                    currentMaplatLng = new LatLng(mGoogleMap.getCameraPosition().target.latitude, mGoogleMap.getCameraPosition().target.longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setUpGoogleMap(GoogleMap googleMap, boolean showMarker, SupportMapFragment mapFragment) {
        this.showMarker = showMarker;
        this.mGoogleMap = googleMap;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mGoogleMap.setMapStyle(new MapStyleOptions(CustomMapTheme.mapStyle));
                mGoogleMap.setMyLocationEnabled(true);
            }
        });
    }

    public void getLocation(final Response.Listener<LatLng> listener){
        this.listener=listener;
    }
    public PickLocation(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(fragmentActivity);
        geocoder = new Geocoder(fragmentActivity, Locale.getDefault());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.e("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                    mLastLocation = location;
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker.remove();
                    }
                    //Place current location marker
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    if(listener!=null)
                    listener.onResponse(latLng);
                }
            }

        };
        checkLocation();

    }

    public void searchByAutoComplete() {
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, Arrays.asList(Place.Field.LAT_LNG)).build(fragmentActivity);
        fragmentActivity.startActivityForResult(intent, SHARED_KEY_REQUESTS.PLACE_AUTOCOMPLETE_REQUEST);
    }

    private void checkLocation() {
        LocationManager lm = (LocationManager) fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(fragmentActivity);
            dialog.setMessage("Please enable GPS.");
            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    fragmentActivity.startActivityForResult(myIntent, SHARED_KEY_REQUESTS.OPEN_GPS_REQUEST);
                    //get gps
                }
            });
            dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

        } else {
            if (!checkLocationPermission())
                return;
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(120000); // two minute interval
            mLocationRequest.setFastestInterval(120000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private boolean checkLocationPermission() {
        boolean b = true;
        if (ContextCompat.checkSelfPermission(fragmentActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(fragmentActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // fragmentActivity thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                b = false;
                new AlertDialog.Builder(fragmentActivity)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(fragmentActivity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        SHARED_KEY_REQUESTS.LOCATION_PERMISSION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(fragmentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        SHARED_KEY_REQUESTS.LOCATION_PERMISSION);
            }
        }
        return b;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SHARED_KEY_REQUESTS.LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(fragmentActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(fragmentActivity, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHARED_KEY_REQUESTS.OPEN_GPS_REQUEST) {
            checkLocation();
        } else if (requestCode == SHARED_KEY_REQUESTS.PLACE_AUTOCOMPLETE_REQUEST) {
            if (resultCode == RESULT_OK) {
                setLocationInMap(Autocomplete.getPlaceFromIntent(data).getLatLng());
            }
        }
    }

}
