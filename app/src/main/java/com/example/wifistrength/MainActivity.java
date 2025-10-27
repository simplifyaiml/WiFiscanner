package com.example.wifistrength;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView listView;
    private WifiListAdapter wifiAdapter;
    private List<ScanResult> results = new ArrayList<>();
    private Handler handler = new Handler();
    private Switch mockSwitch;

    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final int REFRESH_INTERVAL_MS = 5000; // 5 seconds

    private final Runnable scanRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mockSwitch.isChecked()) startScan(); else mockWifiList();
            handler.postDelayed(this, REFRESH_INTERVAL_MS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.wifiListView);
        mockSwitch = findViewById(R.id.mockSwitch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiAdapter = new WifiListAdapter(this, results);
        listView.setAdapter(wifiAdapter);

        mockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Mock mode — update immediately from mock data
                    mockWifiList();
                } else {
                    checkPermissions();
                }
            }
        });

        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_CODE);
        } else {
            startAutoRefresh();
        }
    }

    private void startAutoRefresh() {
        handler.post(scanRunnable);
    }

    private void startScan() {
        try {
            registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            boolean success = wifiManager.startScan();
            if (!success) {
                Toast.makeText(this, "Scan failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error starting scan: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private final BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                unregisterReceiver(this);
            } catch (IllegalArgumentException ignored) {}
            List<ScanResult> scanResults = wifiManager.getScanResults();
            Collections.sort(scanResults, (a, b) -> Integer.compare(b.level, a.level));

            results.clear();
            results.addAll(scanResults);
            wifiAdapter.notifyDataSetChanged();
        }
    };

    private void mockWifiList() {
        // Create simple mock ScanResult-like objects via a lightweight helper class in adapter
        results.clear();
        results.add(createMock("Home_Network", -45, "WPA2"));
        results.add(createMock("Office_WiFi", -60, "WPA3"));
        results.add(createMock("Open_Cafe", -75, "Open"));
        results.add(createMock("HiddenNet", -85, "WEP"));
        wifiAdapter.notifyDataSetChanged();
    }

    private ScanResult createMock(String ssid, int level, String caps) {
        ScanResult mock = new ScanResult();
        try {
            // Some fields are public so we can set them directly
            mock.SSID = ssid;
            mock.level = level;
            mock.capabilities = caps;
        } catch (Exception ignored) {}
        return mock;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(scanRunnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startAutoRefresh();
        } else {
            Toast.makeText(this, "Permission required to scan WiFi networks.", Toast.LENGTH_LONG).show();
        }
    }
}
