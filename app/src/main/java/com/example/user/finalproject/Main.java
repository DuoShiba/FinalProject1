package com.example.user.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.TimeUnit;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;
import com.android.volley.AuthFailureError;
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
        Bitmap bitmap;
        Bundle bundle=new Bundle();

        String URLV ="https://graduation-107.appspot.com/vision";
        String URLS ="https://graduation-107.appspot.com/search";
        ProgressDialog progressDialog;
        String Json=new String();

        Intent intentforresult=new Intent();
//    Button      testmaps;
        ImageView iv1;
        TextView TextView;
        RequestQueue rQueue;
        JSONObject jsonObject = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera =(ImageButton)findViewById(R.id.camera);
        picture =(ImageButton)findViewById(R.id.picture);
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
            startActivityForResult(Intent.createChooser(intent, "Select Image"), GET_FROM_PICTURE);
          /* intent.setClass(Main.this,ResultActivity.class);
           startActivity(intent);*/

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

    private View.OnClickListener M = new View.OnClickListener() {
        Uri outputFileUri;
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Main.this,MapResultActivity.class);
            startActivityForResult(intent,3);
        }

    };

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        progressDialog = new ProgressDialog(Main.this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();
        super.onActivityResult(requestCode, resultCode, data);
        Rcode=requestCode;
        Intent intent=new Intent();

        if (data == null) return;
        bitmap = (Bitmap) data.getExtras().get("data");


        if (requestCode == GET_FROM_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
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

                Toast.makeText(Main.this, "Uploaded Successful" + s, Toast.LENGTH_LONG).show();


                       Json=s;
                        try {
                       jsonObject=new JSONObject(s);
//                       Object jsonOb = jsonObject.getJSONObject("label_annotations");
                       System.out.println(jsonObject);
                        }catch(Exception e){
                            System.out.println("Error: " + e.getMessage());
                        }

                if(Rcode == GET_FROM_CAMERA)
                {
                    intentforresult.putExtra("json",Json);
                    intentforresult.setClass(Main.this,TextResultActivity.class);
                    startActivityForResult(intentforresult,101);
                }

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

        rQueue.add(requestforvision) ;

    }
}
