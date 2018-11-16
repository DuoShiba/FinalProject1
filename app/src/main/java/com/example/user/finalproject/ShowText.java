package com.example.user.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowText extends AppCompatActivity {
    TextView T1;
    String alltext=new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_show_text);
        Intent intent =getIntent();
        alltext=intent.getStringExtra("showtext");
        System.out.println(alltext);
        T1=(TextView)findViewById(R.id.Q23321);
        T1.setText(alltext);
    }
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
