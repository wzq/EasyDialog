package com.wzq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by wzq on 15/9/2.
 */
public class EasyDialog extends Dialog implements View.OnClickListener {

    public static final int BUTTON_CONFIRM = 0x10;
    public static final int BUTTON_CANCEL = 0x20;
    private View rootView;

    private SuccessTickView successTickView;

    private CircularProgressBar circularProgressBar;

    private TextView title, content;

    private ImageView errorView;

    private Animation animIn;

    private Animation animOut, errorIn;

    private OnActionListener mListener;

    private TextView cancel, confirm;

    private String mTitleText, mContentText, mConfirmText, mCancelText;

    public static final int TYPE_LOADING = 0;

    public static final int TYPE_SUCCESS = 1;

    public static final int TYPE_ERROR = 2;
    private int type = -1;

    public void changeType(int b) {
        switch (b) {
            case TYPE_LOADING:
                circularProgressBar.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                successTickView.setVisibility(View.GONE);
                break;
            case TYPE_SUCCESS:
                circularProgressBar.setVisibility(View.GONE);
                successTickView.setVisibility(View.VISIBLE);
                successTickView.startAnimation();
                break;
            case TYPE_ERROR:
                circularProgressBar.setVisibility(View.GONE);
                successTickView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                startErrorAnimation();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onAction(this, view.getId() == R.id.cancel_button ? BUTTON_CANCEL : BUTTON_CONFIRM);
        } else {
            dismissWithAnimation();
        }
    }

    public interface OnActionListener {
        void onAction(EasyDialog eDialog, int bType);
    }

    public static EasyDialog build(Context context, int type) {
        return new EasyDialog(context, R.style.alert_dialog, type);
    }

    public EasyDialog(Context context, int themeResId,int type) {
        super(context, themeResId);
        this.type = type;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        errorIn = AnimationUtils.loadAnimation(context, R.anim.error_x_in);
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
        circularProgressBar = (CircularProgressBar) findViewById(R.id.dialog_progress);
        successTickView = (SuccessTickView) findViewById(R.id.dialog_success);
        cancel = (TextView) findViewById(R.id.cancel_button);
        confirm = (TextView) findViewById(R.id.confirm_button);
        title = (TextView) findViewById(R.id.dialog_title);
        content = (TextView) findViewById(R.id.dialog_content);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        errorView = (ImageView) findViewById(R.id.dialog_error);


        setTextInView(title, mTitleText);
        setTextInView(content, mContentText);
        setTextInView(confirm, mConfirmText);
        setTextInView(cancel, mCancelText);
        changeType(type);

    }

    private void setTextInView(TextView v, String s){
        if(!TextUtils.isEmpty(s))v.setText(s);
        v.setVisibility(TextUtils.isEmpty(s)?View.GONE:View.VISIBLE);
    }

    public EasyDialog setTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        if (title != null ) {
            setTextInView(title, mTitleText);
        }
        return this;
    }

    public EasyDialog setContentText(String mContentText) {
        this.mContentText = mContentText;
        if (content != null ) {
            setTextInView(content, mContentText);
        }
        return this;
    }


    public EasyDialog setCancelText(String mCancelText) {
        this.mCancelText = mCancelText;
        if (cancel != null) {
            setTextInView(cancel, mCancelText);
        }
        return this;
    }


    public EasyDialog setConfirmText(String mConfirmText) {
        this.mConfirmText = mConfirmText;
        if (confirm != null)
            setTextInView(confirm, mConfirmText);
        return this;
    }

    public EasyDialog setOnActionListener(OnActionListener listener){
        this.mListener = listener;
        return this;
    }

    public EasyDialog setIsCancelable(boolean b1, boolean b2){
        setCancelable(b1);
        setCanceledOnTouchOutside(b2);
        return this;
    }


    @Override
    protected void onStart() {
        showWithAnimation();
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


    public void startErrorAnimation(){
        errorView.startAnimation(errorIn);
    }
}
