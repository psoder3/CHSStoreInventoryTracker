package com.blikoon.qrcodescannerlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.blikoon.qrcodescanner.QrCodeActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ModifySalePriceActivity extends Activity {

    private Button button_next;
    private Button button_cancel;
    private EditText salePriceInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sale_price);
        button_cancel = (Button) findViewById(R.id.cancel_button);
        button_next = (Button) findViewById(R.id.confirm_button);
        salePriceInput = (EditText) findViewById(R.id.editTextSalePrice);
        TextView productNameView = (TextView) findViewById(R.id.productTextView);
        final String result = getIntent().getExtras().getString("barcodeValue");
        final double salePrice = getIntent().getExtras().getDouble("salePrice");
        // get product name, size, and price
        //final String[] args = result.split(" - ");

        productNameView.setText(result);
        salePriceInput.setText(salePrice+"");

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // package a Data object for post
                InventoryObject o = new InventoryObject();
                o.barcodeValue = result;
                o.salePrice = Double.parseDouble(salePriceInput.getText().toString());
                String urlAddress = "http://104.236.169.62:80/schoolStore/modifySalePrice";
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
