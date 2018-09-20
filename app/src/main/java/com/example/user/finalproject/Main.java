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
import java.util.Random;

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
        testmaps=(Button)findViewById(R.id.button2) ;
        camera.setOnClickListener(Random);
        picture.setOnClickListener(Random);
        testmaps.setOnClickListener(Random);
    }
    private View.OnClickListener P = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Main.this,ResultActivity.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener C = new View.OnClickListener() {
        Uri outputFileUri;
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Main.this,TextResultActivity.class);
            startActivityForResult(intent,2);
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

    private View.OnClickListener Random = new View.OnClickListener() {
        Uri outputFileUri;
        java.util.Random ran = new Random();
        int num=0;
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            num=(ran.nextInt(3))+1;
            System.out.println(num);
            if(num==0)
            {
                System.out.println("ERROR");
            }
            else if(num==1) {
                intent.setClass(Main.this,ResultActivity.class);
            }
            else if(num==2) {
                intent.setClass(Main.this,TextResultActivity.class);
            }
            else if(num==3) {
                intent.setClass(Main.this, MapResultActivity.class);
            }
            startActivityForResult(intent,4);
        }

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
