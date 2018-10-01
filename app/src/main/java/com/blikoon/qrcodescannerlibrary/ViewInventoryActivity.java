package com.blikoon.qrcodescannerlibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ViewInventoryActivity extends Activity {

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        int requestCode = getIntent().getExtras().getInt("requestCode");

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        getInventory(requestCode);
    }


    public void getInventory(final int requestCode)
    {
        String urlParam = "";
        String url = "http://104.236.169.62:80/schoolStore/getInventory";

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
                                AlertDialog alertDialog = new AlertDialog.Builder(ViewInventoryActivity.this).create();
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

    public void continueAfterSuccessfulResponse(JSONObject jsonObj, int requestCode)
    {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            JSONArray inventory_list = jsonObj.getJSONArray("items");
            //Adding 2 TextViews
            for (int i = 0; i < inventory_list.length(); i++) {
                JSONObject obj = inventory_list.getJSONObject(i);
                //String barcode = obj.getString("barcode");
                String productName = obj.getString("productName");
                String size = obj.getString("size");
                int quantity = obj.getInt("quantity");
                Double price = obj.getDouble("salePrice");
                TextView textView = new TextView(this);
                String itemText = "";
                if (requestCode == MainActivity.REQUEST_CODE_VIEW_CURRENT_PRICES)
                {
                    itemText += productName + " (" + size + "):   $" + df.format(price);
                }
                else if (requestCode == MainActivity.REQUEST_CODE_VIEW_CURRENT_INVENTORY)
                {
                    itemText += productName + " (" + size + "):    " + quantity + " available";
                }
                textView.setText(itemText);
                linearLayout.addView(textView);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
