package com.example.abhishek.aidl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishek.aidl2.R;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface service;
    private RemoteServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectService();

        Button addProduct = (Button) findViewById(R.id.btnAdd);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (service != null) {
                        String name = ((EditText) findViewById(R.id.edtName)).getText().toString();
                        int quatity = Integer.parseInt(((EditText) findViewById(R.id.edtQuantity)).getText().toString());
                        float cost = Float.parseFloat(((EditText) findViewById(R.id.edtCost)).getText().toString());

                        service.addProduct(name, quatity, cost);
                        Toast.makeText(MainActivity.this, "Product added.", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(MainActivity.this, "Service is not connected", Toast.LENGTH_LONG)
                                .show();
                    }
                } catch (Exception e) {

                }
            }
        });

        Button searchProduct = (Button) findViewById(R.id.btnSearch);
        searchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (service != null) {
                        String name = ((EditText) findViewById(R.id.edtSearchProduct)).getText().toString();
                        Product product = service.getProduct(name);
                        if (product != null) {
                            ((TextView) findViewById(R.id.txtSearchResult)).setText(product.toString());
                        } else {
                            Toast.makeText(MainActivity.this, "No product found with this name", Toast.LENGTH_LONG)
                                    .show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "Service is not connected", Toast.LENGTH_LONG)
                                .show();
                    }
                } catch (Exception e) {

                }
            }
        });


    }


    private void connectService() {
        serviceConnection = new RemoteServiceConnection();
        Intent i = new Intent("com.example.abhishek.aidl.ProductService");
        i.setPackage("com.example.abhishek.aidl");
        boolean ret = bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    class RemoteServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IMyAidlInterface.Stub.asInterface((IBinder) boundService);
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_LONG)
                    .show();
        }
    }
}


