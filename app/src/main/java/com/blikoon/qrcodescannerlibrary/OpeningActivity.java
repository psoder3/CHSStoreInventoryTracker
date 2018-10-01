package com.blikoon.qrcodescannerlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OpeningActivity extends Activity {

    Button button_open_store;
    Button button_view_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        ImageView imageView = (ImageView) findViewById(R.id.logo);
        imageView.setImageResource(R.drawable.clearfield_logo);

        button_open_store = (Button) findViewById(R.id.button_open_store);
        button_open_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an inventory view activity
                Intent i = new Intent(OpeningActivity.this,MainActivity.class);
                startActivity(i);
            }
        });



        button_view_history = (Button) findViewById(R.id.button_view_history);


    }
}
