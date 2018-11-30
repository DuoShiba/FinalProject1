package com.example.user.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ResultActivity extends AppCompatActivity {
    int GET_FROM_CAMERA=1;
    int GET_FROM_PICTURE=2;
    ImageView Original;
    ImageView Img1,Img2;
    AlertDialog.Builder adb;
    String values=new String();
    String Im1=new String(),Im2=new String();
    String l1=new String(),l2=new String();
    Intent intent=new Intent();
    TextView tv;
    TextView ti1,ti2;
    TextView s1,s2;
    Button si1,si2;
    Button L1,L2;
    Uri uri1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Intent i = new Intent(Intent.ACTION_PICK, null);
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(i,GET_FROM_PICTURE);*/

        super.onCreate(savedInstanceState);
         intent=this.getIntent();
         values=intent.getStringExtra("search");
        l1=intent.getStringExtra("searchL1");
        l2=intent.getStringExtra("searchL2");
        Im1=intent.getStringExtra("searchI1");
        Im2=intent.getStringExtra("searchI2");

//        bitmap=bundle.getParcelable("picture");
        setContentView(R.layout.activity_result);
        Img1=(ImageView)findViewById(R.id.imageButton1);
        Img2=(ImageView)findViewById(R.id.imageButton2);
        ti1=(TextView)findViewById(R.id.Rtitle1);
        ti2=(TextView)findViewById(R.id.Rtitle2);
        s1=(TextView)findViewById(R.id.Rsnippet1);
        s2=(TextView)findViewById(R.id.Rsnippet2);


//        Original=(ImageView)findViewById(R.id.imageView2);
        adb=new AlertDialog.Builder(this);
        ti1.setText(intent.getStringExtra("search1"));
        ti2.setText(intent.getStringExtra("search2"));
        s1.setText(intent.getStringExtra("searchS1"));
        s2.setText(intent.getStringExtra("searchS2"));
        Img1.setOnClickListener(Goout1);
        Img2.setOnClickListener(Goout2);

        Picasso.with(this).load(Im1).into(Img1);
        Picasso.with(this).load(Im2).into(Img2);
    }
    private View.OnClickListener Goout1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intents1=new Intent();
            intents1.setAction(Intent.ACTION_VIEW);
            intents1.setData(Uri.parse(l1));
            startActivity(intents1);
        }
    };
    private View.OnClickListener Goout2 = new View.OnClickListener() {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
