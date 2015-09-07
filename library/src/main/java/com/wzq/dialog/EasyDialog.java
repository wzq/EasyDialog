package com.wzq.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * Created by wzq on 15/9/2.
 */
public class EasyDialog extends Dialog implements View.OnClickListener {

    private View rootView, buttons;

    private SuccessTickView successTickView;

    private CircularProgressBar circularProgressBar;

    private TextView title, content;

    private ImageView errorView;

    private Animation animIn;

    private Animation animOut, errorIn;

    private OnActionListener mListener;

    private Button cancel, confirm;

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
                buttons.setVisibility(View.GONE);
                successTickView.setVisibility(View.GONE);
                break;
            case TYPE_SUCCESS:
                circularProgressBar.setVisibility(View.GONE);
                buttons.setVisibility(View.VISIBLE);
                successTickView.setVisibility(View.VISIBLE);
                successTickView.startAnimation();
                break;
            case TYPE_ERROR:
                circularProgressBar.setVisibility(View.GONE);
                buttons.setVisibility(View.VISIBLE);
                successTickView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                startErrorAnimation();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onClick(this);
        } else {
            dismissWithAnimation();
        }
    }

    public interface OnActionListener {
        void onClick(EasyDialog dialog);
    }

    public static EasyDialog build(Context context, int type, OnActionListener listener) {
        return new EasyDialog(context, R.style.alert_dialog, listener, type);
    }

    public EasyDialog(Context context, int themeResId, final OnActionListener mListener, int type) {
        super(context, themeResId);
        this.mListener = mListener;
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
        buttons = findViewById(R.id.dialog_buttons);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.dialog_progress);
        successTickView = (SuccessTickView) findViewById(R.id.dialog_success);
        cancel = (Button) findViewById(R.id.cancel_button);
        confirm = (Button) findViewById(R.id.confirm_button);
        title = (TextView) findViewById(R.id.dialog_title);
        content = (TextView) findViewById(R.id.dialog_content);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        errorView = (ImageView) findViewById(R.id.dialog_error);


        title.setText(mTitleText);
        content.setText(mContentText);
        confirm.setText(mConfirmText);
        cancel.setText(mCancelText);
        changeType(type);

    }

    public String getTitleText() {
        return mTitleText;
    }

    public EasyDialog setTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        if (title != null && !TextUtils.isEmpty(mTitleText)) {
            title.setText(mTitleText);
        }
        return this;
    }

    public String getContentText() {
        return mContentText;
    }

    public EasyDialog setContentText(String mContentText) {
        this.mContentText = mContentText;
        if (content != null && !TextUtils.isEmpty(mContentText)) {
            content.setText(mContentText);
            content.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public EasyDialog setCancelText(String mCancelText) {
        this.mCancelText = mCancelText;
        if (cancel != null && !TextUtils.isEmpty(mCancelText)) {
            cancel.setText(mCancelText);
            cancel.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public EasyDialog setConfirmText(String mConfirmText) {
        this.mConfirmText = mConfirmText;
        if (confirm != null && !TextUtils.isEmpty(mConfirmText)) {
            confirm.setText(mConfirmText);
            confirm.setVisibility(View.VISIBLE);
        }
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
