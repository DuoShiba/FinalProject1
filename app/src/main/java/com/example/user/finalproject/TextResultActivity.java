package com.example.user.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextResultActivity extends AppCompatActivity {
    //TextView t1= (TextView)findViewById (R.id.active);
    int GET_FROM_CAMERA=1;
    int GET_FROM_PICTURE=2;
    ImageView Original;
    Button alltext,Refresh;
    ImageView Img1,Img2;
    AlertDialog.Builder adb;
    String values=new String();
    String t1=new String(),t2=new String();
    String l1=new String(),l2=new String();
    Intent send=new Intent();
    TextView tv;
    TextView ti1,ti2;
    TextView s1,s2;
    Button si1,si2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* //讀取網路圖片，型態為Bitmap

        private static Bitmap getBitmapFromURL(String imageUrl)
        {
            try
            {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }*/

        super.onCreate(savedInstanceState);
        //t1.setMovementMethod(ScrollingMovementMethod.getInstance());
       /* Intent Iintent = new Intent();
        Iintent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Iintent,2);*/

        send=getIntent();
        values=send.getStringExtra("search");
        send.putExtra("showtext",values);
        l1=send.getStringExtra("searchL1");
        l2=send.getStringExtra("searchL2");
        System.out.println(1233);
        System.out.println(send.getStringExtra("search1")+send.getStringExtra("searchS1"));


        adb=new AlertDialog.Builder(this);
        setContentView(R.layout.activity_text_result);
        alltext =(Button)findViewById(R.id.button);
//        Refresh=(Button)findViewById(R.id.button2);
        Img1=(ImageView)findViewById(R.id.imageButton);
        Img2=(ImageView)findViewById(R.id.imageButton2);
//        Original=(ImageView)findViewById(R.id.imageView1);
        tv=(TextView)findViewById(R.id.active);

        ti1=(TextView)findViewById(R.id.title1);
        ti2=(TextView)findViewById(R.id.title2);
        s1=(TextView)findViewById(R.id.snippet1);
        s2=(TextView)findViewById(R.id.snippet2);
//        si1=(Button) findViewById(R.id.link1);
//        si2=(TextView)findViewById(R.id.snippet2);

        Img1.setOnClickListener(GoOUT);
        Img2.setOnClickListener(I1);
        alltext.setOnClickListener(S);
//        Refresh.setOnClickListener(I2);
        tv.setText(values);
        ti1.setText(send.getStringExtra("search1"));
        s1.setText(send.getStringExtra("searchS1"));
        ti2.setText(send.getStringExtra("search2"));
        s2.setText(send.getStringExtra("searchS2"));
       /* si1.setText("網站連結1");
        si1.setOnClickListener(GoOUT);*/
//        tv.setText("dsadasdads");

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

    private View.OnClickListener I1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intents1=new Intent();
            intents1.setAction(Intent.ACTION_VIEW);
            intents1.setData(Uri.parse(l2));
            startActivity(intents1);
/*         adb.setMessage(DataGetter(R.drawable.camera));
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.show();*/
        }

    };


    private View.OnClickListener I2 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            refresh();

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
    public void refresh() {

        onCreate(null);

    }
}
