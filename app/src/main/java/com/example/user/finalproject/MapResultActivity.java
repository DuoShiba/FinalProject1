package com.example.user.finalproject;

import android.graphics.Color;
import android.graphics.Path;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapResultActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String NowLoc = "Now Location";
    private String TarLoc = "Target Location";
    private double  Nowlong=24.180112, Nowlat=120.648317;
    private double  Tarlong=24.177364, Tarlat=120.645998;
    private PolylineOptions polylineOptions = new PolylineOptions();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_result);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       /* if(checkmapversion()==true)
        {
            initialMaps();
        }*/
    }
        /*
            private  void initialMaps()
            {
            }
            private boolean checkmapversion(){
                int avai = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapResultActivity.this);
                if(avai == ConnectionResult.SUCCESS)
                {
                    Log.i("google map","version right");
                    return true;
                }
                else
                {
                    Toast.makeText(this,"version not the same",Toast.LENGTH_LONG).show();
                    return false;
                }
            }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(Nowlong, Nowlat);            //標示    第一個參數為經度   第二個參數為緯度
        LatLng sydney2 = new LatLng(Tarlong, Tarlat);
        Marker mark1 = mMap.addMarker(new MarkerOptions()               //設定標示的內容
                .position(sydney)
                .title(NowLoc)                                          //標示的名稱
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        Marker mark2 = mMap.addMarker(new MarkerOptions()
                .position(sydney2)                                                                                          //設定目標地點
                .draggable(true)
                .title(TarLoc)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));                              //標示顏色

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        Polyline line = mMap.addPolyline(new PolylineOptions()                              //畫線
                .add(sydney,sydney2)                                                        //標示一到標示二
                .width(10)                                                                  //線條寬度
                .color(Color.BLACK));                                                     //線條顏色
    }}
