package com.example.user.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResultActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String NowLoc = "現在位置";
    private String TarLoc = "目的地";
    private double  Nowlong=34.989168, Nowlat=135.759365;
    private double  Tarlong=35.039330, Tarlat=135.729249;
    private PolylineOptions polylineOptions = new PolylineOptions();
    private String routes;
    Intent get=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        get=getIntent();
        routes=get.getStringExtra("route");
        this.Tarlong=Double.parseDouble(get.getStringExtra("LAT"));
        this.Tarlat=Double.parseDouble(get.getStringExtra("LNG"));
        System.out.println(routes);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_result);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        ParserTask parserTask = new ParserTask();
        parserTask.execute(routes);
        mapFragment.getMapAsync(this);


    }

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
        /*Polyline line = mMap.addPolyline(new PolylineOptions()                              //畫線
                .add(sydney,sydney2)                                                        //標示一到標示二
                .width(10)                                                                  //線條寬度
                .color(Color.BLACK));                                                     //線條顏色*/
    }




    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
                System.out.println("do in background:" + routes);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(3);

                // Changing the color polyline according to the mode
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
        public class DirectionsJSONParser {
            /**
             * Receives a JSONObject and returns a list of lists containing latitude and
             * longitude
             */
            public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

                List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
                JSONArray jRoutes = null;
                JSONArray jLegs = null;
                JSONArray jSteps = null;

                try {

                    jRoutes = jObject.getJSONArray("routes");

                    /** Traversing all routes */
                    for (int i = 0; i < jRoutes.length(); i++) {
                        jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                        List path = new ArrayList<HashMap<String, String>>();

                        /** Traversing all legs */
                        for (int j = 0; j < jLegs.length(); j++) {
                            jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                            /** Traversing all steps */
                            for (int k = 0; k < jSteps.length(); k++) {
                                String polyline = "";
                                polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                        .get(k)).get("polyline")).get("points");
                                List<LatLng> list = decodePoly(polyline);

                                /** Traversing all points */
                                for (int l = 0; l < list.size(); l++) {
                                    HashMap<String, String> hm = new HashMap<String, String>();
                                    hm.put("lat",
                                            Double.toString(((LatLng) list.get(l)).latitude));
                                    hm.put("lng",
                                            Double.toString(((LatLng) list.get(l)).longitude));
                                    path.add(hm);
                                }
                            }
                            routes.add(path);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }
                return routes;
            }

            /**
             * Method to decode polyline points Courtesy :
             * jeffreysambells.com/2010/05/27
             * /decoding-polylines-from-google-maps-direction-api-with-java
             * */
            private List<LatLng> decodePoly(String encoded) {

                List<LatLng> poly = new ArrayList<LatLng>();
                int index = 0, len = encoded.length();
                int lat = 0, lng = 0;

                while (index < len) {
                    int b, shift = 0, result = 0;
                    do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lat += dlat;

                    shift = 0;
                    result = 0;
                    do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                    } while (b >= 0x20);
                    int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                    lng += dlng;

                    LatLng p = new LatLng((((double) lat / 1E5)),
                            (((double) lng / 1E5)));
                    poly.add(p);
                }
                return poly;
            }
        }

    }

}
