package com.blikoon.qrcodescannerlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.blikoon.qrcodescanner.QrCodeActivity;

public class ReturnActivity extends Activity {

    private Button button_next;
    private Button button_cancel;
    private NumberPicker number_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        button_cancel = (Button) findViewById(R.id.cancel_button);
        button_next = (Button) findViewById(R.id.confirm_button);
        number_picker = (NumberPicker) findViewById(R.id.number_picker);
        number_picker.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        number_picker.setMaxValue(1000);
        number_picker.setValue(1);

        TextView productNameView = (TextView) findViewById(R.id.productTextView);
        final String result = getIntent().getExtras().getString("barcodeValue");
        // get product name, size, and price
        final String[] args = result.split(" - ");

        productNameView.setText(result);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // package a Data object for post
                InventoryObject o = new InventoryObject();
                o.barcodeValue = result;
                o.productName = args[0];
                o.size = args[1];
                o.salePrice = Double.parseDouble(args[2]);
                o.quantity = number_picker.getValue();
                String urlAddress = "http://104.236.169.62:80/schoolStore/return";
                MainActivity.sendPost(urlAddress,o);
                finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
