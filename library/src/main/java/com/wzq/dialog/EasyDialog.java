package com.wzq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by wzq on 15/9/2.
 */
public class EasyDialog extends Dialog {

    private View rootView;

    private Context context;

    public EasyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public static EasyDialog build(Context context){
        return  new EasyDialog(context, R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_easy_dialog);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

    }

    @Override
    protected void onStart() {
        showWithAnimation();
    }

    @Override
    public void cancel() {

    }

    public void showWithAnimation(){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.modal_in);
        rootView.startAnimation(animation);
    }
}
