package com.example.user.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class TextResultActivity extends AppCompatActivity {
    //TextView t1= (TextView)findViewById (R.id.active);
    int GET_FROM_CAMERA=1;
    int GET_FROM_PICTURE=2;
    ImageView Original;
    Button alltext;
    ImageView Img1,Img2,Img3;
    AlertDialog.Builder adb;
    String values=new String();
    String t1=new String(),t2=new String(),t3=new String();
    String l1=new String(),l2=new String(),l3=new String();
    String Im1=new String(),Im2=new String(),Im3=new String();
    Intent send=new Intent();
    TextView tv;
    TextView ti1,ti2,ti3;
    TextView s1,s2,s3;
    Button si1,si2,si3;
    Button L1,L2,L3;
    Uri uri1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //t1.setMovementMethod(ScrollingMovementMethod.getInstance());
       /* Intent Iintent = new Intent();
        Iintent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Iintent,2);*/

        send=getIntent();
        values=send.getStringExtra("json");
        send.putExtra("showtext",values);
        l1=send.getStringExtra("searchL1");
        l2=send.getStringExtra("searchL2");
        l3=send.getStringExtra("searchL3");
        Im1=send.getStringExtra("searchI1");
        Im2=send.getStringExtra("searchI2");
        Im3=send.getStringExtra("searchI3");

//        System.out.println(send.getStringExtra("search1")+send.getStringExtra("searchS1"));


        adb=new AlertDialog.Builder(this);
        setContentView(R.layout.activity_text_result);
        alltext =(Button)findViewById(R.id.button);
        L1=(Button)findViewById(R.id.button1);
        L2=(Button)findViewById(R.id.button2);
        L3=(Button)findViewById(R.id.button3);
        Img1=(ImageView)findViewById(R.id.image1);
        Img2=(ImageView)findViewById(R.id.image2);
        Img3=(ImageView)findViewById(R.id.image3);
//        Original=(ImageView)findViewById(R.id.imageView);
        tv=(TextView)findViewById(R.id.active);

        ti1=(TextView)findViewById(R.id.title1);
        ti2=(TextView)findViewById(R.id.title2);
        ti3=(TextView)findViewById(R.id.title3);
        s1=(TextView)findViewById(R.id.snippet1);
        s2=(TextView)findViewById(R.id.snippet2);
        s3=(TextView)findViewById(R.id.snippet3);


        L1.setOnClickListener(GoOUT);
        L2.setOnClickListener(GoOUT2);
        L3.setOnClickListener(GoOUT3);
        alltext.setOnClickListener(S);

        tv.setText(values);
        ti1.setText(send.getStringExtra("search1"));
        s1.setText(send.getStringExtra("searchS1"));
        ti2.setText(send.getStringExtra("search2"));
        s2.setText(send.getStringExtra("searchS2"));
        ti3.setText(send.getStringExtra("search3"));
        s3.setText(send.getStringExtra("searchS3"));
        Picasso.with(this).load(Im1).into(Img1);
        Picasso.with(this).load(Im2).into(Img2);
        Picasso.with(this).load(Im3).into(Img3);

    }


    private View.OnClickListener S = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent();
            send.setClass(TextResultActivity.this,ShowText.class);
            startActivity(send);
        }
        Intent intent;

    };

    private View.OnClickListener GoOUT= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intents1=new Intent();
            intents1.setAction(Intent.ACTION_VIEW);
            intents1.setData(Uri.parse(l1));
            startActivity(intents1);
        }

    };
    private View.OnClickListener GoOUT2 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intents1=new Intent();
            intents1.setAction(Intent.ACTION_VIEW);
            intents1.setData(Uri.parse(l2));
            startActivity(intents1);
        }
    };
    private View.OnClickListener GoOUT3 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intents1=new Intent();
            intents1.setAction(Intent.ACTION_VIEW);
            intents1.setData(Uri.parse(l3));
            startActivity(intents1);
        }
    };




    private String DataGetter (int input)
    {
        return "This is not finish yet";
    };


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

    }
    @Override
    protected void onResume()
    {

        super.onResume();

    }

}
