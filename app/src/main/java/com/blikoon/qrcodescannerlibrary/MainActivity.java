package com.blikoon.qrcodescannerlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blikoon.qrcodescanner.QrCodeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    private Button button_sell;
    private Button button_return;
    private Button button_restock;
    private Button button_add_product;
    private Button button_modify_sale_price;
    private Button button_view_current_prices;
    private Button button_view_current_inventory;


    private static final int REQUEST_CODE_SELL = 101;
    private static final int REQUEST_CODE_RETURN = 102;
    private static final int REQUEST_CODE_RESTOCK = 103;
    private static final int REQUEST_CODE_ADD_PRODUCT = 104;
    private static final int REQUEST_CODE_MODIFY_SALE_PRICE = 105;


    private final String LOGTAG = "QRCScanner-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_sell = (Button) findViewById(R.id.button_sell);
        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the qr scan activity
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_SELL);
            }
        });

        button_return = (Button) findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the qr scan activity
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_RETURN);
            }
        });

        button_restock = (Button) findViewById(R.id.button_restock);
        button_restock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the qr scan activity
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_RESTOCK);
            }
        });

        button_add_product = (Button) findViewById(R.id.button_add_product);
        button_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the qr scan activity
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_ADD_PRODUCT);
            }
        });

        button_modify_sale_price = (Button) findViewById(R.id.button_modify_sale_price);
        button_modify_sale_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the qr scan activity
                Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_MODIFY_SALE_PRICE);
            }
        });

        button_view_current_prices = (Button) findViewById(R.id.button_view_current_prices);
        button_view_current_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a price view activity
                //Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                //startActivityForResult( i,REQUEST_CODE_MODIFY_SALE_PRICE);
            }
        });

        button_view_current_inventory = (Button) findViewById(R.id.button_view_current_inventory);
        button_view_current_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show an inventory view activity
                //Intent i = new Intent(MainActivity.this,QrCodeActivity.class);
                //startActivityForResult( i,REQUEST_CODE_MODIFY_SALE_PRICE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            Log.d(LOGTAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }

        if(data==null)
            return;

        //Getting the passed result
        final String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
        Log.d(LOGTAG,"Have scan result in your app activity :"+ result);

        /*
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Scan result");
        alertDialog.setMessage(result);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        */

        // send http post request with data
        /*
        */
        if(requestCode == REQUEST_CODE_ADD_PRODUCT)
        {
            Intent i = new Intent(MainActivity.this,AddProductActivity.class);
            i.putExtra("barcodeValue", result);
            startActivityForResult( i,REQUEST_CODE_ADD_PRODUCT);
        }
        else
        {
            sendGet(result, requestCode);
        }

    }

    public void continueAfterSuccessfulResponse(JSONObject jsonObj, int requestCode)
    {
        String barcode = null;
        try {
            barcode = jsonObj.getString("barcode");
            if (requestCode == REQUEST_CODE_SELL)
            {
                Intent i = new Intent(MainActivity.this,SellActivity.class);
                i.putExtra("barcodeValue", barcode);
                startActivityForResult( i,REQUEST_CODE_SELL);

            }
            else if (requestCode == REQUEST_CODE_RETURN)
            {
                Intent i = new Intent(MainActivity.this,ReturnActivity.class);
                i.putExtra("barcodeValue", barcode);
                startActivityForResult( i,REQUEST_CODE_RETURN);
            }
            else if (requestCode == REQUEST_CODE_RESTOCK)
            {
                Intent i = new Intent(MainActivity.this,RestockActivity.class);
                i.putExtra("barcodeValue", barcode);
                startActivityForResult( i,REQUEST_CODE_RESTOCK);
            }
            else if (requestCode == REQUEST_CODE_MODIFY_SALE_PRICE)
            {
                double salePrice = jsonObj.getDouble("salePrice");
                Intent i = new Intent(MainActivity.this, ModifySalePriceActivity.class);
                i.putExtra("barcodeValue", barcode);
                i.putExtra("salePrice", salePrice);
                startActivityForResult(i, REQUEST_CODE_MODIFY_SALE_PRICE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void sendGet(final String barcode, final int requestCode)
    {
        String urlParam = "";
        String url = "http://104.236.169.62:80/schoolStore/getItemInfo/";
        try {
            urlParam = URLEncoder.encode(barcode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url += urlParam;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String responseResult = jsonObj.getString("result");
                            String err = jsonObj.getString("err");
                            if (responseResult.equals("success")) {
                                continueAfterSuccessfulResponse(jsonObj, requestCode);
                            }
                            else
                            {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage(err);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static void sendPost(final String urlAddress, final InventoryObject data) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAddress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("productName", data.productName);
                    jsonParam.put("quantity", data.quantity);
                    jsonParam.put("size", data.size);
                    jsonParam.put("salePrice", data.salePrice);
                    jsonParam.put("manufacturedPrice", data.manufacturedPrice);


                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
