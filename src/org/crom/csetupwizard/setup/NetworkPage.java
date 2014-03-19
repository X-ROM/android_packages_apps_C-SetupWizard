/*
 *  Copyright (C) 2014 The C-RoM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.crom.csetupwizard.setup;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import org.crom.csetupwizard.MainActivity;
import org.crom.csetupwizard.csetupwizard.R;

public class NetworkPage extends Fragment {

    public static final String LOCATION_PROVIDERS_ALLOWED = "location_providers_allowed";

    private CheckBox mWifiCheckBox;
    private CheckBox mDataCheckBox;
    private CheckBox mGpsCheckBox;

    private WifiManager mWifiManager;
    private ConnectivityManager mConnManager;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.page_network, null);
        mWifiCheckBox = (CheckBox) view.findViewById(R.id.network_enable_wifi);
        mDataCheckBox = (CheckBox) view.findViewById(R.id.network_enable_data);
        mGpsCheckBox = (CheckBox) view.findViewById(R.id.network_enable_gps);

        MyOnCheckedChangeListener listener = new MyOnCheckedChangeListener();
        mWifiCheckBox.setOnCheckedChangeListener(listener);
        mDataCheckBox.setOnCheckedChangeListener(listener);
        mGpsCheckBox.setOnCheckedChangeListener(listener);

        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mConnManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        mGpsCheckBox.setChecked(getGpsState());
        updateWifiSettings();
        updateDataSettings();

        return view;
    }

    private boolean getGpsState() {
        ContentResolver cr = mContext.getContentResolver();
        return Settings.Secure.isLocationProviderEnabled(cr, LocationManager.GPS_PROVIDER);
    }

    private void setGpsState(boolean isChecked) {
        ContentResolver cr = mContext.getContentResolver();
        Settings.Secure.setLocationProviderEnabled(cr, LocationManager.GPS_PROVIDER, isChecked);
    }

    private void updateWifiSettings() {
        mWifiCheckBox.setChecked(mWifiManager.isWifiEnabled());
    }

    private void updateDataSettings() {
        mDataCheckBox.setEnabled(!mWifiCheckBox.isChecked());
        mDataCheckBox.setChecked(mConnManager.getMobileDataEnabled());
    }

    @Override
    public void onResume() {
        super.onResume();

        updateWifiSettings();
        updateDataSettings();
    }

    private class MyOnCheckedChangeListener implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (((MainActivity) getActivity()).isFirstPage()) {
                return;
            }
            if (buttonView == mWifiCheckBox) {
                if (isChecked) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.android.settings",
                            "com.android.settings.Settings$WifiSettingsActivity"));
                    startActivity(intent);
                } else {
                    mWifiManager.setWifiEnabled(false);
                    updateDataSettings();
                }
            } else if (buttonView == mDataCheckBox) {
                mConnManager.setMobileDataEnabled(isChecked);
            } else if (buttonView == mGpsCheckBox) {
                setGpsState(isChecked);
            }

        }

    }
}
