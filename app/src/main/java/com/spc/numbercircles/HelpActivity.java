package com.spc.numbercircles;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

public class HelpActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void dismiss(View v) {
        finish();
    }
}
