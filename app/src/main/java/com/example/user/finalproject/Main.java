package com.example.user.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class Main extends AppCompatActivity {
    ImageButton camera;
    ImageButton picture;
    Button      testmaps;
    ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera =(ImageButton)findViewById(R.id.camera);
        picture =(ImageButton)findViewById(R.id.picture);
        testmaps=(Button)findViewById(R.id.button) ;
        camera.setOnClickListener(C);
        picture.setOnClickListener(P);
    }
    private View.OnClickListener P = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Main.this,ResultActivity.class);
            startActivity(intent);
        }
        Intent intent;

    };
    private View.OnClickListener C = new View.OnClickListener() {
        Uri outputFileUri;
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Main.this,TextResultActivity.class);
            startActivityForResult(intent,2);
        }
        Intent intent;

    };

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        if(requestCode==2) {
            super.onActivityResult(requestCode, resultCode, intent);

            if (intent == null) return;
            if (requestCode != 1) return;

            Bitmap bm = (Bitmap) intent.getExtras().get("data");
            iv1.setImageBitmap(bm);
        }
    }
}
