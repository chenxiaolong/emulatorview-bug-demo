package com.github.emulatorview_bug_demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jackpal.androidterm.emulatorview.EmulatorView;
import jackpal.androidterm.emulatorview.TermSession;

public class MainActivity extends ActionBarActivity {
    private static TermSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mSession == null) {
            mSession = new TermSession();
            // Ignore input
            mSession.setTermOut(new ByteArrayOutputStream());

            try {
                mSession.setTermIn(getAssets().open("test.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        EmulatorView emulatorView = (EmulatorView) findViewById(R.id.terminal);
        emulatorView.attachSession(mSession);
        emulatorView.setDensity(metrics);
    }

    @Override
    public void finish() {
        super.finish();
        if (mSession != null) {
            mSession.finish();
            mSession = null;
        }
    }
}
