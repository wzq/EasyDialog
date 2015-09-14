package com.wzq.dialog.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wzq.dialog.SuccessAnimViewX;

/**
 * Created by wzq on 15/9/14.
 */
public class TestActivity extends AppCompatActivity{

    SuccessAnimViewX animViewX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        animViewX = (SuccessAnimViewX) findViewById(R.id.test);
        animViewX.startAnim(600);
    }
}
