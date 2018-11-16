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
import android.widget.ImageView;

public class ResultActivity extends AppCompatActivity {
    int GET_FROM_CAMERA=1;
    int GET_FROM_PICTURE=2;
    ImageView Original;
    ImageView Img1,Img2;
    AlertDialog.Builder adb;
    Bundle bundle;
    Bitmap bitmap;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Intent i = new Intent(Intent.ACTION_PICK, null);
        i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(i,GET_FROM_PICTURE);*/

        super.onCreate(savedInstanceState);
        intent=this.getIntent();
        bundle=intent.getExtras();
        System.out.println(bundle.getString("1"));
//        bitmap=bundle.getParcelable("picture");
        setContentView(R.layout.activity_result);
        Img1=(ImageView)findViewById(R.id.imageButton);
        Img2=(ImageView)findViewById(R.id.imageButton2);
        Original=(ImageView)findViewById(R.id.imageView2);
        adb=new AlertDialog.Builder(this);
        Img1.setOnClickListener(I1);
        Img2.setOnClickListener(I1);
    }
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
    private String DataGetter (int input)
    {
        return "This is not finish yet";
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == GET_FROM_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                Original.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
    /*protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
//        if(requestCode==2) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent == null) return;
        Bitmap bm = (Bitmap) intent.getExtras().get("data");
        Original.setImageBitmap(bm);

//        }
    }*/
}
