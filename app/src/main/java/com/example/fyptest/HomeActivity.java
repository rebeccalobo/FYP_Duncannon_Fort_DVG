package com.example.fyptest;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiowa.bluetoothlocatability.bleApi.UserLocationCapability;
import com.kiowa.bluetoothlocatability.utilities.Constants;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        checkPermissions();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.CURRENT_LOCATION.equals(intent.getAction())) {
                Log.i("BLE", "broadcast received");
                String receivedText = intent.getStringExtra("com.kiowa.EXTRA_TEXT");
                //int deviceName = intent.getIntExtra("DeviceName");
                //Log.i("BLE", "Closest beacon = "+deviceName);
            }
        }

    };

    public void checkPermissions() {
        int getPermissionRequestBluetooth = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
        int getPermissionRequestBluetoothAdmin = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
        int getPermissionRequestCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int getPermissionRequestBgLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            if (getPermissionRequestBgLocation == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1234);
            }
        }
        if (getPermissionRequestCoarseLocation == PackageManager.PERMISSION_DENIED || getPermissionRequestBluetoothAdmin == PackageManager.PERMISSION_DENIED || getPermissionRequestBluetooth == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION}, 1234);

        }


    }
}



