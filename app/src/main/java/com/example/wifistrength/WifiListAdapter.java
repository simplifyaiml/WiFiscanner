package com.example.wifistrength;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WifiListAdapter extends BaseAdapter {

    private Context context;
    private List<ScanResult> wifiList;

    public WifiListAdapter(Context context, List<ScanResult> wifiList) {
        this.context = context;
        this.wifiList = wifiList;
    }

    @Override
    public int getCount() { return wifiList.size(); }

    @Override
    public Object getItem(int position) { return wifiList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    private int rssiToPercent(int rssi) {
        int percent = 2 * (rssi + 100);
        return Math.max(0, Math.min(100, percent));
    }

    private int getSignalIcon(int rssi) {
        int strength = rssiToPercent(rssi);
        if (strength > 80) return R.drawable.ic_signal_4;
        if (strength > 60) return R.drawable.ic_signal_3;
        if (strength > 40) return R.drawable.ic_signal_2;
        return R.drawable.ic_signal_1;
    }

    private String getSecurityType(ScanResult result) {
        String caps = result.capabilities == null ? "" : result.capabilities;
        if (caps.contains("WPA3")) return "WPA3";
        if (caps.contains("WPA2")) return "WPA2";
        if (caps.contains("WPA")) return "WPA";
        if (caps.contains("WEP")) return "WEP";
        return "Open";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            row = LayoutInflater.from(context).inflate(R.layout.list_item_wifi, parent, false);
        } else {
            row = convertView;
        }

        ScanResult wifi = wifiList.get(position);

        TextView ssidText = row.findViewById(R.id.ssidText);
        TextView detailsText = row.findViewById(R.id.detailsText);
        ImageView signalIcon = row.findViewById(R.id.signalIcon);

        String ssid = wifi.SSID == null || wifi.SSID.isEmpty() ? "<Hidden SSID>" : wifi.SSID;
        int level = wifi.level;
        int percent = rssiToPercent(level);
        String security = getSecurityType(wifi);

        ssidText.setText(ssid);
        detailsText.setText(String.format("%s | %d dBm (%d%%)", security, level, percent));
        signalIcon.setImageResource(getSignalIcon(level));

        return row;
    }
}
