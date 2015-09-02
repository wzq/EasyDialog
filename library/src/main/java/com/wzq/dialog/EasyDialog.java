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

    private SuccessTickView successTickView;

    private Animation animIn;

    private Animation animOut;

    private OnActionListener mListener;

    public interface OnActionListener {
        void onClick(EasyDialog dialog);
    }

    public static EasyDialog build(Context context) {
        return new EasyDialog(context, R.style.alert_dialog, null);
    }


    public EasyDialog(Context context, int themeResId, final OnActionListener mListener) {
        super(context, themeResId);
        this.mListener = mListener;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        animIn = AnimationUtils.loadAnimation(context, R.anim.modal_in);
        animOut = AnimationUtils.loadAnimation(context, R.anim.modal_out);
        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               rootView.post(new Runnable() {
                   @Override
                   public void run() {
                       EasyDialog.super.dismiss();
                   }
               });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_easy_dialog);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        successTickView = (SuccessTickView) findViewById(R.id.success);
    }

    @Override
    protected void onStart() {
        showWithAnimation();
        //successTickView.startTickAnim();
    }

    @Override
    public void dismiss() {
        dismissWithAnimation();
    }

    public void showWithAnimation() {
        rootView.startAnimation(animIn);
    }

    public void dismissWithAnimation() {
        rootView.startAnimation(animOut);
    }
}
