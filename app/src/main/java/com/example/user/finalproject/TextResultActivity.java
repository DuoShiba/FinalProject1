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
    ImageView Img1,Img2;
    AlertDialog.Builder adb;
    String values=new String();
    String t1=new String(),t2=new String();
    String l1=new String(),l2=new String();
    String Im1=new String(),Im2=new String();
    Intent send=new Intent();
    TextView tv;
    TextView ti1,ti2;
    TextView s1,s2;
    Button si1,si2;
    Button L1,L2;
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
        Im1=send.getStringExtra("searchI1");
        Im2=send.getStringExtra("searchI2");

//        System.out.println(send.getStringExtra("search1")+send.getStringExtra("searchS1"));


        adb=new AlertDialog.Builder(this);
        setContentView(R.layout.activity_text_result);
        alltext =(Button)findViewById(R.id.button);
        L1=(Button)findViewById(R.id.button1);
        L2=(Button)findViewById(R.id.button2);
        Img1=(ImageView)findViewById(R.id.image1);
        Img2=(ImageView)findViewById(R.id.image2);
//        Original=(ImageView)findViewById(R.id.imageView);
        tv=(TextView)findViewById(R.id.active);

        ti1=(TextView)findViewById(R.id.title1);
        ti2=(TextView)findViewById(R.id.title2);
        s1=(TextView)findViewById(R.id.snippet1);
        s2=(TextView)findViewById(R.id.snippet2);
//        si1=(Button) findViewById(R.id.link1);
//        si2=(TextView)findViewById(R.id.snippet2);


        L1.setOnClickListener(GoOUT);
        L2.setOnClickListener(GoOUT2);
        alltext.setOnClickListener(S);
//        Refresh.setOnClickListener(I2);
        tv.setText(send.getStringExtra("json"));
        ti1.setText(send.getStringExtra("search1"));
        s1.setText(send.getStringExtra("searchS1"));
        ti2.setText(send.getStringExtra("search2"));
        s2.setText(send.getStringExtra("searchS2"));
        Picasso.with(this).load(Im1).into(Img1);
        Picasso.with(this).load(Im2).into(Img2);
       /* si1.setText("網站連結1");
        si1.setOnClickListener(GoOUT);*/
//        tv.setText("dsadasdads");
//        Img1.setImageURI(uri1.parse("https://zh.wikipedia.org/wiki/%E6%9F%B4%E7%8A%AC#/media/File:Shiba_Inu.jpg"));
//        Picasso.with(this).load("https://zh.wikipedia.org/wiki/%E6%9F%B4%E7%8A%AC#/media/File:Shiba_Inu.jpg").into(Original);
//        new DownloadImageTask((ImageButton)findViewById(R.id.imageButton)).execute("https://zh.wikipedia.org/wiki/%E6%9F%B4%E7%8A%AC#/media/File:Shiba_Inu.jpg");
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



        //網址轉圖片測試
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
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
