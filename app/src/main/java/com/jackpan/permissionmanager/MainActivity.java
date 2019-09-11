package com.jackpan.permissionmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jackpan.permissionmanagerlib.CheckDeviceManagr;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckDeviceManagr checkDeviceManagr = new CheckDeviceManagr();
        checkDeviceManagr.isCheckVersion(this,MainActivity.class);

        }
}
