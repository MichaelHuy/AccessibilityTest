package com.identiv.accesstest;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayDeque;
import java.util.Deque;

public class GlobalActionBarService extends AccessibilityService {
    FrameLayout mLayout;
    WindowManager wm;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("EVENT", "Huy ");
        if (mLayout != null) {
            Log.i("EVENT", "Huy inner");
//            mLayout.setVisibility(0);
//            wm.removeView(mLayout);
            mLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        // Create an overlay and display the action bar
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayout = new FrameLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.action_bar, mLayout);
        wm.addView(mLayout, lp);
//        configurePowerButton();
        configureVolumeButton();
//        wm.removeView(mLayout);
        Log.i("EVENT", "Huy onServiceConnected");
//        mLayout.setVisibility(View.GONE);
    }

//    private void configurePowerButton() {
//        Button powerButton = (Button) mLayout.findViewById(R.id.power);
//        powerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
//            }
//        });
//    }

    private void configureVolumeButton() {
        Button volumeUpButton = (Button) mLayout.findViewById(R.id.volume_up);
        volumeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            }
        });
    }

}
