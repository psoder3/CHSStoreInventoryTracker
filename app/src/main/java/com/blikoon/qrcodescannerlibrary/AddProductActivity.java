package com.blikoon.qrcodescannerlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.blikoon.qrcodescanner.QrCodeActivity;

public class AddProductActivity extends Activity {

    private Button button_next;
    private Button button_cancel;
    private NumberPicker number_picker;
    private EditText manufacturedPriceInput;
    private EditText salePriceInput;
    private EditText nameInput;
    private EditText sizeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        button_cancel = (Button) findViewById(R.id.cancel_button);
        button_next = (Button) findViewById(R.id.confirm_button);
        number_picker = (NumberPicker) findViewById(R.id.number_picker);
        number_picker.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        number_picker.setMaxValue(1000);
        number_picker.setValue(1);

        manufacturedPriceInput = (EditText) findViewById(R.id.editTextManufacturedPrice);
        salePriceInput = (EditText) findViewById(R.id.editTextSalePrice);
        nameInput = (EditText) findViewById(R.id.editTextPickName);
        sizeInput = (EditText) findViewById(R.id.editTextPickSize);

        TextView productNameView = (TextView) findViewById(R.id.productTextView);
        final String result = getIntent().getExtras().getString("barcodeValue");
        // get product name, size, and price
        final String[] args = result.split(" - ");

        productNameView.setText(result);
        manufacturedPriceInput.setText(args[2]);
        salePriceInput.setText(args[2]);
        nameInput.setText(args[0]);
        sizeInput.setText(args[1]);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // package a Data object for post
                InventoryObject o = new InventoryObject();
                o.barcodeValue = result;
                o.productName = nameInput.getText().toString();
                o.size = sizeInput.getText().toString();
                o.manufacturedPrice = Double.parseDouble(manufacturedPriceInput.getText().toString());
                o.salePrice = Double.parseDouble(salePriceInput.getText().toString());
                o.quantity = number_picker.getValue();
                String urlAddress = "http://104.236.169.62:80/schoolStore/addProduct";
                MainActivity.sendPost(urlAddress, o);
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
