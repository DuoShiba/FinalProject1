package com.example.user.finalproject;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.nfc.Tag;
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
import java.util.Random;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
        int nextResult=0;
        Bitmap bitmap ;
        Bundle bundle=new Bundle();
//        ImageView testIV;

        String URLV ="https://graduation-107.appspot.com/vision";
        String URLS ="https://graduation-107.appspot.com/search";
        String URLT="https://graduation-107.appspot.com/translation";
        ProgressDialog progressDialog;
        String Json=new String();
        String searchString = new String ();
        String searchResult1 = new String ();
        String searchResult2 = new String ();
        String searchSnippet1 = new String ();
        String searchSnippet2 = new String ();
        String searchLink1 = new String ();
        String searchLink2 = new String ();

        Intent intentforresult=new Intent();
//    Button      testmaps;
        ImageView iv1;
        TextView TextView;
        RequestQueue rQueue,sQueue,tQueue;
        JSONObject jsonObject = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera =(ImageButton)findViewById(R.id.camera);
        picture =(ImageButton)findViewById(R.id.picture);
//        testIV = (ImageView)findViewById(R.id.imageView) ;
//        testmaps=(Button)findViewById(R.id.button2) ;
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
    protected void onActivityResult(final int requestCode, int resultCode, Intent data)
    {
        progressDialog = new ProgressDialog(Main.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();
        super.onActivityResult(requestCode, resultCode, data);
        Rcode=requestCode;
        Intent intent=new Intent();

        if (data == null) return;

        if(requestCode==GET_FROM_CAMERA&& resultCode == RESULT_OK&&data!=null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            System.out.println(123);
        }

        else if (requestCode == GET_FROM_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null)
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

        //converting image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


        StringRequest requestforvision = new StringRequest(Request.Method.POST, URLV, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();

//                Toast.makeText(Main.this, "Uploaded Successful" + s, Toast.LENGTH_LONG).show();

                       Json=s;

//                        System.out.println(Json.substring(Json.lastIndexOf("best_guess_labels")+32, Json.length()-8));



//                        以下開始推到 google translation
                    StringRequest requestfortranslation = new StringRequest(Request.Method.GET, URLT+"/"+Json.substring(Json.lastIndexOf("best_guess_labels")+32, Json.length()-8), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(Main.this,response,Toast.LENGTH_LONG).show();
                            //解析中文字成網址編碼
                            try {
                                // 進行 URL 百分比編碼
                                String storeUTF=new String();
                                storeUTF = URLEncoder.encode(response,"UTF-8");
                                URLS=URLS+"/"+storeUTF.substring(storeUTF.indexOf("Translation")+11);
                                // 輸出結果
//                                System.out.println(URLS);

                            } catch (UnsupportedEncodingException e) {
                                // 例外處理 ...
                            }
                               //以下開始推到google search
                        StringRequest requestforsearch = new StringRequest(Request.Method.GET, URLS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                          System.out.println(response.substring(response.indexOf("title",response.indexOf("items"))+8,response.indexOf("htmlTitle")-6));
//                                          System.out.println(response.substring(response.indexOf("link",response.indexOf("items"))+8,response.indexOf("displayLink")-6));
                                            System.out.println(response);

                                          //抓取第一筆搜尋
                                          searchResult1=response.substring(response.indexOf("title",response.indexOf("items"))+8,response.indexOf("htmlTitle")-6);
                                          searchLink1=response.substring(response.indexOf("link",response.indexOf("items"))+8,response.indexOf("displayLink")-7);
                                          searchSnippet1=response.substring(response.indexOf("snippet",response.indexOf("items"))+9,response.indexOf("htmlSnippet")-7);

                                         System.out.println(searchResult1);
                                         System.out.println(searchLink1);
                                         System.out.println(searchSnippet1);
                                          nextResult=response.indexOf("title",response.indexOf("items"))+8;
                                          //抓取第二筆搜尋
                                           searchResult2=response.substring(response.indexOf("title",response.indexOf("title",response.indexOf("items"))+8)+8,response.indexOf("htmlTitle",response.indexOf("htmlTitle",response.indexOf("items"))+8)-6);
                                            searchLink2=response.substring(response.indexOf("link",response.indexOf("link",response.indexOf("items"))+8)+8,response.indexOf("displayLink",response.indexOf("displayLink",response.indexOf("items"))+8)-7);
                                            searchSnippet2=response.substring(response.indexOf("snippet",response.indexOf("snippet",response.indexOf("items"))+8)+9,response.indexOf("htmlSnippet",response.indexOf("htmlSnippet",response.indexOf("items"))+8)-7);
                                            System.out.println(searchResult2);
                                            System.out.println(searchLink2);
                                            System.out.println(searchSnippet2);
//                                            System.out.println(response.indexOf("htmlTitle",response.indexOf("htmlTitle",response.indexOf("items"))+8)-6);

                                          searchString=response;



                                    intentforresult.putExtra("search",searchString);
                                    intentforresult.putExtra("search1",searchResult1);
                                    intentforresult.putExtra("searchL1",searchLink1);
                                    intentforresult.putExtra("searchS1",searchSnippet1);
                                    intentforresult.putExtra("search2",searchResult2);
                                    intentforresult.putExtra("searchL2",searchLink2);
                                    intentforresult.putExtra("searchS2",searchSnippet2);
                                    intentforresult.putExtra("json",Json);
                                    intentforresult.setClass(Main.this,TextResultActivity.class);
                                    startActivityForResult(intentforresult,101);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Main.this, "Some error occurred -> "+error, Toast.LENGTH_LONG).show();
                            }
                        });   //search 部分結束
                         RequestQueue sQueue = Volley.newRequestQueue(Main.this);
                         requestforsearch.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                         sQueue.add(requestforsearch);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Main.this, "Some error occurred -> "+error, Toast.LENGTH_LONG).show();
                        }
                    }); //translation 部分結束
                RequestQueue sQueue = Volley.newRequestQueue(Main.this);
                requestfortranslation.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                sQueue.add(requestfortranslation);


            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Main.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();
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
        requestforvision.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue.add(requestforvision) ;

    }
    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {

        }
        return bm;
    }
}
