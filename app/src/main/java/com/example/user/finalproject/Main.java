package com.example.user.finalproject;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Delayed;

public class Main extends AppCompatActivity {
        ImageButton camera;
        ImageButton picture;
        int GET_FROM_CAMERA=1;
        int GET_FROM_PICTURE=2;
        int Rcode=-1;
        Bitmap bitmap ;
        Bundle bundle=new Bundle();
//        ImageView testIV;

        String URLV ="https://graduation-107.appspot.com/vision";
        String URLS ="https://graduation-107.appspot.com/search";
        String URLT="https://graduation-107.appspot.com/translation";
        String URLM="https://maps.googleapis.com/maps/api/directions/json?origin=Tokyo&destination=Taiwan&mode=transit&key=AIzaSyAfmj2MsB037nIMOB_27-EL5ohCPsLDNmQ&mode=walking&avoid=tolls|highways|ferries";
        String URLM2="https://maps.googleapis.com/maps/api/directions/json?origin=&destination=Taiwan&waypoints=Joplin,MO|Oklahoma+City,OK&key=AIzaSyBkHRIH7NsdzUbz30VSqlTEpsHx3PZ5FDo";//forTest
        String URLMforLoc="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=AIzaSyC3mNWr85_4cYzYsJ-X60S5s_DwQJv5hmU&input=Tokyo&inputtype=textquery";
        String URLMforLoc2="https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyC3mNWr85_4cYzYsJ-X60S5s_DwQJv5hmU";
        ProgressDialog progressDialog;
        String Json=new String();
        String Trans=new String();



        String searchString = new String ();
        String searchResult1 = new String ();
        String searchResult2 = new String ();
        String searchResult3 = new String ();
        String searchSnippet1 = new String ();
        String searchSnippet2 = new String ();
        String searchSnippet3 = new String ();
        String searchLink1 = new String ();
        String searchLink2 = new String ();
        String searchLink3 = new String ();
        String searchImg1 = new String();
        String searchImg2 = new String();
        String searchImg3 = new String();


        LatLng origi=new LatLng(34.989168,135.759365);
        LatLng desti=new LatLng(35.039330,135.729249);

        Intent intentforresult=new Intent();
//    Button      testmaps;
        ImageView iv1;
        TextView TextView;
        RequestQueue rQueue,sQueue,tQueue,mQueue;
        JSONObject jsonObject = new JSONObject();
        int nextImg=0,nextResult=0,nextSnippet=0,nextLink=0;
        int LnextImg=0,LnextResult=0,LnextSnippet=0,LnextLink=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera =(ImageButton)findViewById(R.id.camera);
        picture =(ImageButton)findViewById(R.id.picture);
//        testIV = (ImageView)findViewById(R.id.imageView) ;
//        testmaps=(Button)findViewById(R.id.button2);
        camera.setOnClickListener(C);
        picture.setOnClickListener(P);
//        testmaps.setOnClickListener(Random);
    }


    private View.OnClickListener P = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image"),GET_FROM_PICTURE);

        }
    };

    private View.OnClickListener C = new View.OnClickListener() {
        Uri outputFileUri;
        @Override
        public void onClick(View v) {
               Intent Iintent = new Intent();
                Iintent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Iintent,GET_FROM_CAMERA);
        }

    };


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        progressDialog = new ProgressDialog(Main.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();
        super.onActivityResult(requestCode, resultCode, data);
        Rcode = requestCode;
        final Intent intent = new Intent();

        if (data == null) return;

        if (requestCode == GET_FROM_CAMERA && resultCode == RESULT_OK && data != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
//            System.out.println(123);
        } else if (requestCode == GET_FROM_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            Uri filePath = data.getData();
            System.out.println(filePath);

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

//                Setting image to ImageView
//               testIV.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == GET_FROM_PICTURE || requestCode == GET_FROM_CAMERA) {

            //converting image to base64 string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


            StringRequest requestforvision = new StringRequest(Request.Method.POST, URLV, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
//                    progressDialog.dismiss();

//                Toast.makeText(Main.this, "Uploaded Successful" + s, Toast.LENGTH_LONG).show();

                    Json = s;
//                    System.out.println(Json);

                  /*  if(Json.indexOf("language_code")!=-1) //若偵測到中文
                    {
                         Trans=Json.substring(Json.lastIndexOf("best_guess_labels") + 32,Json.indexOf("language_code")-6);
                         Toast.makeText(Main.this,Trans,Toast.LENGTH_LONG).show();
                         URLS=URLS + "/" +Trans;

                        Toast.makeText(Main.this,"Nothing",Toast.LENGTH_LONG).show();
                        //                        以下開始推到 google translation
                        if(Json.indexOf("language_code",Json.indexOf("best_guess_labels"))==-1)
                        {
                            Trans=Json.substring(Json.lastIndexOf("best_guess_labels") + 32, Json.length() - 8);
                        }
                        else
                        {
                            Trans=Json.substring(Json.lastIndexOf("best_guess_labels") + 32,Json.indexOf("language_code")-6);
                        }
                         Toast.makeText(Main.this,Trans,Toast.LENGTH_LONG).show();

                        StringRequest requestfortranslation = new StringRequest(Request.Method.GET, URLT + "/" + Trans, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                            Toast.makeText(Main.this, response, Toast.LENGTH_LONG).show();


                                //解析中文字成網址編碼
                                try {
                                    // 進行 URL 百分比編碼
                                    String storeUTF = new String();
                                    storeUTF = URLEncoder.encode(response, "UTF-8");
                                    URLS = URLS + "/" + storeUTF.substring(storeUTF.indexOf("Translation") + 11);
                                    // 輸出結果
//                                System.out.println(URLS);

                                } catch (UnsupportedEncodingException e) {
                                    // 例外處理 ...
                                }

                                //以下開始推到google search
                                StringRequest requestforsearch = new StringRequest(Request.Method.GET, URLS, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
//                                        System.out.println(response);
                                        //抓取第一筆搜尋
                                        searchResult1 = response.substring(response.indexOf("title", response.indexOf("items")) + 8, response.indexOf("htmlTitle") - 6);
                                        searchLink1 = response.substring(response.indexOf("link", response.indexOf("items")) + 8, response.indexOf("displayLink") - 7);
                                        searchSnippet1 = response.substring(response.indexOf("snippet", response.indexOf("items")) + 9, response.indexOf("htmlSnippet") - 7);
                                        searchImg1 = response.substring(response.indexOf("cse_image") + 35, response.indexOf("kind", response.indexOf("cse_image")) - 33);


                                        nextResult = response.indexOf("title", response.indexOf("items")) + 8;
                                        nextImg = response.indexOf("cse_image") + 35;


                                        //抓取第二筆搜尋
                                        searchResult2 = response.substring(response.indexOf("title", response.indexOf("title", response.indexOf("items")) + 8) + 8, response.indexOf("htmlTitle", response.indexOf("htmlTitle", response.indexOf("items")) + 8) - 6);
                                        searchLink2 = response.substring(response.indexOf("link", response.indexOf("link", response.indexOf("items")) + 8) + 8, response.indexOf("displayLink", response.indexOf("displayLink", response.indexOf("items")) + 8) - 7);
                                        searchSnippet2 = response.substring(response.indexOf("snippet", response.indexOf("snippet", response.indexOf("items")) + 8) + 9, response.indexOf("htmlSnippet", response.indexOf("htmlSnippet", response.indexOf("items")) + 8) - 7);
                                        searchImg2 = response.substring(response.indexOf("cse_image", nextImg) + 35, response.indexOf("kind", response.indexOf("cse_image", nextImg)) - 33);

                                        LnextResult=response.indexOf("htmlTitle", response.indexOf("htmlTitle", response.indexOf("items")) + 8) - 6;
                                        LnextLink=response.indexOf("displayLink", response.indexOf("displayLink", response.indexOf("items")) + 8) - 7;
                                        LnextSnippet=response.indexOf("htmlSnippet", response.indexOf("htmlSnippet", response.indexOf("items")) + 8) - 7;
                                        LnextImg= response.indexOf("kind", response.indexOf("cse_image", nextImg)) - 33;

                                        nextResult=response.indexOf("title", response.indexOf("title", response.indexOf("items")) + 8) + 8;
                                        nextLink=response.indexOf("link", response.indexOf("link", response.indexOf("items")) + 8) + 8;
                                        nextSnippet=response.indexOf("snippet", response.indexOf("snippet", response.indexOf("items")) + 8) + 9;
                                        nextImg=response.indexOf("cse_image", nextImg) + 35;

//                                        System.out.println(response.indexOf("title",nextResult+8)+8);
//                                        System.out.println(response.indexOf("htmlTitle",LnextResult+8));

                                        //抓取第三筆搜尋
                                        searchResult3 = response.substring(nextResult,LnextResult);
                                        searchLink3 = response.substring(nextLink,LnextLink);
                                        searchSnippet3 = response.substring(nextSnippet,LnextSnippet);
                                        searchImg3 = response.substring(nextImg, LnextImg);

//                                        System.out.println( searchResult3);
//                                        System.out.println( searchLink3);
//                                        System.out.println(searchSnippet3);
//                                        System.out.println(searchImg3);


                                        searchString = response;


                                        intentforresult.putExtra("search", searchString);
                                        //第一筆
                                        intentforresult.putExtra("search1", searchResult1);
                                        intentforresult.putExtra("searchL1", searchLink1);
                                        intentforresult.putExtra("searchS1", searchSnippet1);
                                        intentforresult.putExtra("searchI1", searchImg1);
                                        //第二筆
                                        intentforresult.putExtra("search2", searchResult2);
                                        intentforresult.putExtra("searchL2", searchLink2);
                                        intentforresult.putExtra("searchS2", searchSnippet2);
                                        intentforresult.putExtra("searchI2", searchImg2);
                                        //第三筆
                                        intentforresult.putExtra("search3", searchResult3);
                                        intentforresult.putExtra("searchL3", searchLink3);
                                        intentforresult.putExtra("searchS3", searchSnippet3);
                                        intentforresult.putExtra("searchI3", searchImg3);

                                        intentforresult.putExtra("json", Json);
                                        //地圖



                                     /*   if(Json.indexOf("landmark_annotations")!=-1)
                                        {
                                            URLM=getDirectionsUrl(origi,desti);

                                            StringRequest requestforMap = new StringRequest(Request.Method.GET, URLM, new Response.Listener<String>() {
                                             @Override
                                             public void onResponse(String response) {
//                                                System.out.println(response+"111111111");
                                                intentforresult.putExtra("route",response);
                                                intentforresult.setClass(Main.this,MapResultActivity.class);
                                                 startActivityForResult(intentforresult, 101);
                                            }
                                             },new Response.ErrorListener(){
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                            }
                                            });
                                             RequestQueue mQueue = Volley.newRequestQueue(Main.this);
                                             requestforMap.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                             mQueue.add(requestforMap);


                                        }
                                        else if (Json.indexOf("text_annotation") != -1) {*/
//                                            intentforresult.putExtra("Text",Json.substring(Json.indexOf("text_annotation"),Json.indexOf("bounding_poly")));
//                                            System.out.println(Json.substring(Json.indexOf("text_annotation"),Json.indexOf("bounding_poly")));
       /*                                 intentforresult.setClass(Main.this, TextResultActivity.class);
                                        startActivityForResult(intentforresult, 101);
//補
                                        } else{
                                            intentforresult.setClass(Main.this, ResultActivity.class);
                                            startActivityForResult(intentforresult, 101);
                                        }//補

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                    }
                                });   //search 部分結束
                                RequestQueue sQueue = Volley.newRequestQueue(Main.this);
                                requestforsearch.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                sQueue.add(requestforsearch);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                            }
                        }); //translation 部分結束
                        RequestQueue sQueue = Volley.newRequestQueue(Main.this);
                        requestfortranslation.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        sQueue.add(requestfortranslation);
                    }

                    //若沒有偵測到中文
                    else
                    {*/
//                        Toast.makeText(Main.this,"Nothing",Toast.LENGTH_LONG).show();
                        //                        以下開始推到 google translation
                       if(Json.indexOf("language_code",Json.indexOf("best_guess_labels"))==-1)
                            {
                                Trans=Json.substring(Json.lastIndexOf("best_guess_labels") + 32, Json.length() - 8);
                            }
                        else
                            {
                                Trans=Json.substring(Json.lastIndexOf("best_guess_labels") + 32,Json.indexOf("language_code")-6);
                            }
//                         Toast.makeText(Main.this,Trans,Toast.LENGTH_LONG).show();

                        StringRequest requestfortranslation = new StringRequest(Request.Method.GET, URLT + "/" + Trans, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                            Toast.makeText(Main.this, response, Toast.LENGTH_LONG).show();


                                //解析中文字成網址編碼
                                try {
                                    // 進行 URL 百分比編碼
                                    String storeUTF = new String();
                                    storeUTF = URLEncoder.encode(response, "UTF-8");
                                    URLS = URLS + "/" + storeUTF.substring(storeUTF.indexOf("Translation") + 11);
                                    // 輸出結果
//                                System.out.println(URLS);

                                } catch (UnsupportedEncodingException e) {
                                    // 例外處理 ...
                              }

                                //以下開始推到google search
                                StringRequest requestforsearch = new StringRequest(Request.Method.GET, URLS, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
//                                        System.out.println(response);
                                        //抓取第一筆搜尋
                                        searchResult1 = response.substring(response.indexOf("title", response.indexOf("items")) + 8, response.indexOf("htmlTitle") - 6);
                                        searchLink1 = response.substring(response.indexOf("link", response.indexOf("items")) + 8, response.indexOf("displayLink") - 7);
                                        searchSnippet1 = response.substring(response.indexOf("snippet", response.indexOf("items")) + 9, response.indexOf("htmlSnippet") - 7);
                                        searchImg1 = response.substring(response.indexOf("cse_image") + 35, response.indexOf("kind", response.indexOf("cse_image")) - 33);


                                        nextResult = response.indexOf("title", response.indexOf("items")) + 8;
                                        nextImg = response.indexOf("cse_image") + 35;


                                        //抓取第二筆搜尋
                                        searchResult2 = response.substring(response.indexOf("title", response.indexOf("title", response.indexOf("items")) + 8) + 8, response.indexOf("htmlTitle", response.indexOf("htmlTitle", response.indexOf("items")) + 8) - 6);
                                        searchLink2 = response.substring(response.indexOf("link", response.indexOf("link", response.indexOf("items")) + 8) + 8, response.indexOf("displayLink", response.indexOf("displayLink", response.indexOf("items")) + 8) - 7);
                                        searchSnippet2 = response.substring(response.indexOf("snippet", response.indexOf("snippet", response.indexOf("items")) + 8) + 9, response.indexOf("htmlSnippet", response.indexOf("htmlSnippet", response.indexOf("items")) + 8) - 7);
                                        searchImg2 = response.substring(response.indexOf("cse_image", nextImg) + 35, response.indexOf("kind", response.indexOf("cse_image", nextImg)) - 33);

                                        LnextResult=response.indexOf("htmlTitle", response.indexOf("htmlTitle", response.indexOf("items")) + 8) - 6;
                                        LnextLink=response.indexOf("displayLink", response.indexOf("displayLink", response.indexOf("items")) + 8) - 7;
                                        LnextSnippet=response.indexOf("htmlSnippet", response.indexOf("htmlSnippet", response.indexOf("items")) + 8) - 7;
                                        LnextImg= response.indexOf("kind", response.indexOf("cse_image", nextImg)) - 33;

                                        nextResult=response.indexOf("title", response.indexOf("title", response.indexOf("items")) + 8) + 8;
                                        nextLink=response.indexOf("link", response.indexOf("link", response.indexOf("items")) + 8) + 8;
                                        nextSnippet=response.indexOf("snippet", response.indexOf("snippet", response.indexOf("items")) + 8) + 9;
                                        nextImg=response.indexOf("cse_image", nextImg) + 35;

//                                        System.out.println(response.indexOf("title",nextResult+8)+8);
//                                        System.out.println(response.indexOf("htmlTitle",LnextResult+8));

                                        //抓取第三筆搜尋
                                        searchResult3 = response.substring(response.indexOf("title",nextResult+20)+8,response.indexOf("htmlTitle",LnextResult+20));
                                        searchLink3 = response.substring(response.indexOf("link", nextLink+20)+8,response.indexOf("displayLink",LnextLink+20));
                                        searchSnippet3 = response.substring(response.indexOf("snippet",nextSnippet+20)+9,response.indexOf("htmlSnippet", LnextSnippet+20));
                                        searchImg3 = response.substring(response.indexOf("cse_image",nextImg+20)+35, response.indexOf("kind", response.indexOf("cse_image", nextImg+20)+20)-33);
//                                        System.out.println(response.indexOf("cse_image",nextImg+20)+35);
//                                        System.out.println(response.indexOf("kind", response.indexOf("cse_image", nextImg+20)+20)-33);

//                                        System.out.println( searchResult3);
//                                        System.out.println( searchLink3);
//                                        System.out.println(searchSnippet3);
//                                        System.out.println(searchImg3);


                                        searchString = response;


                                        intentforresult.putExtra("search", searchString);
                                        //第一筆
                                        intentforresult.putExtra("search1", searchResult1);
                                        intentforresult.putExtra("searchL1", searchLink1);
                                        intentforresult.putExtra("searchS1", searchSnippet1);
                                        intentforresult.putExtra("searchI1", searchImg1);
                                        //第二筆
                                        intentforresult.putExtra("search2", searchResult2);
                                        intentforresult.putExtra("searchL2", searchLink2);
                                        intentforresult.putExtra("searchS2", searchSnippet2);
                                        intentforresult.putExtra("searchI2", searchImg2);
                                        //第三筆
                                        intentforresult.putExtra("search3", searchResult3);
                                        intentforresult.putExtra("searchL3", searchLink3);
                                        intentforresult.putExtra("searchS3", searchSnippet3);
                                        intentforresult.putExtra("searchI3", searchImg3);

                                        intentforresult.putExtra("json", Json);
                                        //地圖



//                                        if(Json.indexOf("landmark_annotations")!=-1)
//                                        {
//
                                        if(Json.indexOf("text_annotations")==-1) {
//                                            URLMforLoc = getmapurl(Trans);
                                            URLMforLoc=getmapurl("Kinkakujicho");
                                            StringRequest requestfotloc = new StringRequest(Request.Method.GET, URLMforLoc, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
//                                                        Toast.makeText(Main.this,response,Toast.LENGTH_LONG).show();
//                                                         System.out.println(response);

                                                    String lat = new String();
                                                    String lng = new String();
                                                    lat = response.substring(response.indexOf("lat") + 7, response.indexOf("lng") - 18);
                                                    lng = response.substring(response.indexOf("lng") + 7, response.indexOf("lng") + 18);
                                                    LatLng target = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                                    URLM = getDirectionsUrl(origi, target);
                                                    intentforresult.putExtra("LAT", lat);
                                                    intentforresult.putExtra("LNG", lng);
                                                    StringRequest requestforMap = new StringRequest(Request.Method.GET, URLM, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
//                                                           System.out.println(response+"111111111");
                                                            intentforresult.putExtra("route", response);
//                                                        intentforresult.setClass(Main.this,MapResultActivity.class);
//                                                            intentforresult.putExtra("text",Json.substring(Json.indexOf("text_annotations")));
                                                            intentforresult.setClass(Main.this, ResultActivity.class);
                                                            startActivityForResult(intentforresult, 101);
                                                            intentforresult.setClass(Main.this, MapResultActivity.class);
                                                            startActivityForResult(intentforresult, 101);
                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                    RequestQueue mQueue = Volley.newRequestQueue(Main.this);
                                                    requestforMap.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                                    mQueue.add(requestforMap);
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                            RequestQueue mlQueue = Volley.newRequestQueue(Main.this);
                                            requestfotloc.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                            mlQueue.add(requestfotloc);
                                        }
                                        else
                                        {
                                            URLMforLoc = getmapurl(Trans);
//                                            URLMforLoc=getmapurl("Kinkakujicho");
                                            StringRequest requestfotloc = new StringRequest(Request.Method.GET, URLMforLoc, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
//                                                        Toast.makeText(Main.this,response,Toast.LENGTH_LONG).show();
//                                                         System.out.println(response);

                                                    String lat = new String();
                                                    String lng = new String();
                                                    lat = response.substring(response.indexOf("lat") + 7, response.indexOf("lng") - 18);
                                                    lng = response.substring(response.indexOf("lng") + 7, response.indexOf("lng") + 18);
                                                    LatLng target = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                                    URLM = getDirectionsUrl(origi, target);
                                                    intentforresult.putExtra("LAT", lat);
                                                    intentforresult.putExtra("LNG", lng);
                                                    StringRequest requestforMap = new StringRequest(Request.Method.GET, URLM, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
//                                                           System.out.println(response+"111111111");
                                                            intentforresult.putExtra("route", response);
//                                                        intentforresult.setClass(Main.this,MapResultActivity.class);
                                                            intentforresult.setClass(Main.this, TextResultActivity.class);
                                                            startActivityForResult(intentforresult, 101);
                                                            intentforresult.setClass(Main.this, MapResultActivity.class);
                                                            startActivityForResult(intentforresult, 101);
                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                    RequestQueue mQueue = Volley.newRequestQueue(Main.this);
                                                    requestforMap.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                                    mQueue.add(requestforMap);
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                            RequestQueue mlQueue = Volley.newRequestQueue(Main.this);
                                            requestfotloc.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                            mlQueue.add(requestfotloc);
                                        }
                                            /*
                                            StringRequest requestforMap = new StringRequest(Request.Method.GET, URLM, new Response.Listener<String>() {
                                             @Override
                                             public void onResponse(String response) {
//                                                System.out.println(response+"111111111");
                                                intentforresult.putExtra("route",response);
                                                intentforresult.setClass(Main.this,MapResultActivity.class);
                                                 startActivityForResult(intentforresult, 101);
                                            }
                                             },new Response.ErrorListener(){
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                            }
                                            });
                                             RequestQueue mQueue = Volley.newRequestQueue(Main.this);
                                             requestforMap.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                             mQueue.add(requestforMap);*/

/*
                                        }
                                        else if (Json.indexOf("text_annotation") != -1) {
//                                            intentforresult.putExtra("Text",Json.substring(Json.indexOf("text_annotation"),Json.indexOf("bounding_poly")));
//                                            System.out.println(Json.substring(Json.indexOf("text_annotation"),Json.indexOf("bounding_poly")));
                                            intentforresult.putExtra("json", Json);
                                            intentforresult.setClass(Main.this, TextResultActivity.class);
                                            startActivityForResult(intentforresult, 101);

                                        } else{
                                            intentforresult.setClass(Main.this, ResultActivity.class);
                                            startActivityForResult(intentforresult, 101);
                                        }*/

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
//                                        Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                                    }
                                });   //search 部分結束
                                RequestQueue sQueue = Volley.newRequestQueue(Main.this);
                                requestforsearch.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                sQueue.add(requestforsearch);

                         }
                       }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Main.this, "Some error occurred -> " + error, Toast.LENGTH_LONG).show();
                            }
                        }); //translation 部分結束
                        RequestQueue sQueue = Volley.newRequestQueue(Main.this);
                        requestfortranslation.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        sQueue.add(requestfortranslation);



                   /* //文字的測試
                    intentforresult.putExtra("json", Json);
                    intentforresult.setClass(Main.this, TextResultActivity.class);
                    startActivityForResult(intentforresult, 101); */ //補

//                    }
                   }
//                        System.out.println(Json.substring(Json.lastIndexOf("best_guess_labels")+32, Json.length()-8));
//                    System.out.println(Json.substring(Json.indexOf("best_guess_labels")));



            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
//                    Toast.makeText(Main.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                }
            }) {
                //adding parameters to send
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("content", imageString);
                    return parameters;
                }
            };

            RequestQueue rQueue = Volley.newRequestQueue(Main.this);
            requestforvision.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue.add(requestforvision);

        }

    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        //Api key
        String key="key=AIzaSyAfmj2MsB037nIMOB_27-EL5ohCPsLDNmQ";

        // Travelling Mode
        String mode = "mode=walking";

        //避免收費站等等
        String avoid ="=tolls|highways|ferries";

//        waypoints,116.32885,40.036675
//        String waypointLatLng = "waypoints="+"40.036675"+","+"116.32885";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + key + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters;
        System.out.println("getDerectionsURL--->: " + url);
        return url;
    }
//---------------------
//    作者：Never-say-Never
//    来源：CSDN
//    原文：https://blog.csdn.net/mad1989/article/details/9734667
//    版权声明：本文为博主原创文章，转载请附上博文链接！
private String getmapurl (String place)
{
    String u=new String() ;
    u=URLMforLoc2+"&query="+place;
    return u;
}



//用以存放地圖經緯度的物件
public class LocfromMap
    {
        private String html_attributions;
        private List<Reslut>resluts;
        public String getHtml_attributions() {
            return html_attributions;
        }

        public void setHtml_attributions(String html_attributions) {
            this.html_attributions = html_attributions;
        }

        public List<Reslut> getResluts() {
            return resluts;
        }

        public void setResluts(List<Reslut> resluts) {
            this.resluts = resluts;
        }

    }

public class Reslut{
        private String result = new String();
        private Geometry geometry = new Geometry();
}
public class Geometry{
        Location location=new Location();
}
public class Location {
        private float Lat = (float) 0.0;
        private float Lng = (float) 0.0;
    public void setLat(float lat) {
        Lat = lat;
    }

    public void setLng(float lng) {
        Lng = lng;
    }
    public float getLat() {
        return Lat;
    }

    public float getLng() {
        return Lng;
    }
}

}
