
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
