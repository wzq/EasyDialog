package com.wzq.dialog.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
                EasyDialog.build(this, EasyDialog.TYPE_ERROR).setTitleText("This is a error dialog.").setCancelText("Yes, close it.").setOnActionListener(this).show();
                break;
            case R.id.b6:
                startActivity(new Intent(this, TestActivity.class));
                break;

        }
    }

    @Override
    public void onAction(EasyDialog dialog, int bType) {
        if (bType == EasyDialog.BUTTON_CANCEL){
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }
}
