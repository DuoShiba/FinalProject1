package com.example.user.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TextResultActivity extends AppCompatActivity {
    //TextView t1= (TextView)findViewById (R.id.active);
    int GET_FROM_CAMERA=1;
    int GET_FROM_PICTURE=2;
    ImageView Original;
    Button alltext,Refresh;
    ImageView Img1,Img2;
    AlertDialog.Builder adb;
    String values=new String();
    Intent send=new Intent();
    TextView tv;
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
        adb=new AlertDialog.Builder(this);
        setContentView(R.layout.activity_text_result);
        alltext =(Button)findViewById(R.id.button);
//        Refresh=(Button)findViewById(R.id.button2);
        Img1=(ImageView)findViewById(R.id.imageButton);
        Img2=(ImageView)findViewById(R.id.imageButton2);
//        Original=(ImageView)findViewById(R.id.imageView1);
        tv=(TextView)findViewById(R.id.active);


        Img1.setOnClickListener(I1);
        Img2.setOnClickListener(I1);
        alltext.setOnClickListener(S);
//        Refresh.setOnClickListener(I2);
        tv.setText(values);
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
            adb.setMessage(DataGetter(R.drawable.camera));
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.show();
        }
        Intent intent;

    };
    private View.OnClickListener I2 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            refresh();

        }
        Intent intent;

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
