/*
 *  Copyright (C) 2013 The ThinkingBridge Project
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

package com.thinkingbridge.setupwizard.setup;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocalePicker.LocaleInfo;
import com.thinkingbridge.setupwizard.MainActivity;
import com.thinkingbridge.setupwizard.R;

import java.util.Locale;

public class WelcomePage extends Fragment {

    private TextView mTextView;
    private Spinner mSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_welcome, null);
        mTextView = (TextView) view.findViewById(R.id.welcome_summary);
        mSpinner = (Spinner) view.findViewById(R.id.local_spinner);

        initView();
        return view;
    }

    // If you have Nexus 5 model.welcome page say Hello Nexus5 User :)
    private void initView() {
        String model = Build.MODEL;
        String originText = " " + mTextView.getText().toString();
        mTextView.setText("Hi, " + model + originText);

        final ArrayAdapter<LocaleInfo> adapter = LocalePicker.constructAdapter(
                getActivity(), R.layout.locale_picker_item, R.id.locale);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (((MainActivity) getActivity()).isFirstPage()) {
                	Locale locale = adapter.getItem(position).getLocale();
                	LocalePicker.updateLocale(locale);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        // Select current locale by default
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int count = adapter.getCount();
                Locale current = Locale.getDefault();
                for (int i = 0; i < count; i++) {
                    Locale locale = adapter.getItem(i).getLocale();
                    if (current.equals(locale)) {
                        mSpinner.setSelection(i);
                        break;
                    }
                }
                mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Locale locale = adapter.getItem(i).getLocale();
                        LocalePicker.updateLocale(locale);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });
    }

}
