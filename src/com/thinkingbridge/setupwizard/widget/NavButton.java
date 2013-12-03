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

package com.thinkingbridge.setupwizard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thinkingbridge.setupwizard.MainActivity;

public class NavButton extends Button {

    private Context mContext;

    public NavButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("WoorimLove", "onclick");
                ((MainActivity) mContext).goNextPage();

            }
        });
    }

}
