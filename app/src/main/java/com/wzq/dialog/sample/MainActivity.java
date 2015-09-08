package com.wzq.dialog.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wzq.dialog.EasyDialog;


public class MainActivity extends AppCompatActivity implements EasyDialog.OnActionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.b1:
                EasyDialog.build(this, EasyDialog.TYPE_SUCCESS).setTitleText("This is a success dialog.").show();
                break;
            case R.id.b2:
                EasyDialog.build(this, EasyDialog.TYPE_ERROR).setTitleText("This is a error dialog.").setCancelText("Yes, close it.").show();
                break;
            case R.id.b3:
                EasyDialog.build(this, EasyDialog.TYPE_LOADING).setTitleText("loading...").setIsCancelable(true, true).show();
                break;
            case R.id.b4:
                final EasyDialog dialog = EasyDialog.build(this, EasyDialog.TYPE_LOADING).setTitleText("loading").setOnActionListener(this);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setTitleText("success").setConfirmText("Yes, do some work.").changeType(EasyDialog.TYPE_SUCCESS);
                    }
                }, 2000);
                break;
            case R.id.b5:
                final EasyDialog dialog1 = EasyDialog.build(this, EasyDialog.TYPE_LOADING).setTitleText("loading");
                dialog1.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog1.setTitleText("error").changeType(EasyDialog.TYPE_ERROR);
                    }
                }, 2000);
                break;


        }
    }

    @Override
    public void onAction(EasyDialog dialog, int bType) {
        if (bType == EasyDialog.BUTTON_CONFIRM){
            System.out.println("confirm");
            dialog.dismiss();
        }
    }
}
