package com.muzi.numberview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by muzi on 2018/4/13.
 * 727784430@qq.com
 */

public class NumberView extends ViewGroup implements View.OnClickListener {

    private CircleImageView btnAdd, btnSub;
    private TextView textNumber;
    private boolean isShowing;
    private int number;
    private int width, height;
    private int margin = 30;
    private int btnSize = 40;
    private int textSize = 20;
    private int textWidth = 50;
    private long startDelay = 200;
    private long duration = 500;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutParams params = new LayoutParams(btnSize, btnSize);

        btnSub = new CircleImageView(getContext());
        btnSub.setImageResource(R.drawable.icon_sub);
        btnSub.setLayoutParams(params);
        btnSub.setId(R.id.btn_sub);
        btnSub.setOnClickListener(this);
        addView(btnSub);

        textNumber = new TextView(getContext());
        textNumber.setTextSize(textSize);
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(textWidth, LayoutParams.WRAP_CONTENT);
        marginLayoutParams.leftMargin = margin;
        marginLayoutParams.rightMargin = margin;
        textNumber.setGravity(Gravity.CENTER);
        textNumber.setLayoutParams(marginLayoutParams);
        addView(textNumber);

        btnAdd = new CircleImageView(getContext());
        btnAdd.setImageResource(R.drawable.icon_add);
        btnAdd.setLayoutParams(params);
        btnAdd.setId(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        addView(btnAdd);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            view.layout(width - view.getMeasuredWidth(), height / 2 - view.getMeasuredHeight() / 2, width, height / 2 + view.getMeasuredHeight() / 2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSize, heightSize;
        int widthMode, heightMode;

        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = btnAdd.getMeasuredWidth() + btnAdd.getPaddingLeft() + btnAdd.getPaddingRight() +
                    btnSub.getMeasuredWidth() + btnSub.getPaddingLeft() + btnSub.getPaddingRight() +
                    textNumber.getMeasuredWidth() + textNumber.getPaddingLeft() + textNumber.getPaddingRight() + margin * 2;
        }

        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int btnHeight = btnAdd.getPaddingBottom() + btnAdd.getPaddingTop() + btnAdd.getMeasuredHeight();
            int textHeight = textNumber.getPaddingBottom() + textNumber.getPaddingTop() + textNumber.getMeasuredHeight();
            height = Math.max(btnHeight, textHeight);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                number++;
                if (!isShowing) {
                    openAnimator();
                } else {
                    textNumber.setText(String.valueOf(number));
                }
                if (listener != null) {
                    listener.onChange(true, number);
                }
                break;
            case R.id.btn_sub:
                if (number > 1) {
                    number--;
                    textNumber.setText(String.valueOf(number));
                } else {
                    number = 0;
                    closeAnimator();
                }
                if (listener != null) {
                    listener.onChange(false, number);
                }
                break;
        }
    }

    /**
     * 开启动画
     */
    private void openAnimator() {
        isShowing = true;
        textNumber.setText(String.valueOf(number));

        btnSub.clearAnimation();
        ObjectAnimator subTranslation = ObjectAnimator.ofFloat(btnSub, "TranslationX", 0, (width - btnSub.getMeasuredWidth()) * -1f);
        ObjectAnimator subRotation = ObjectAnimator.ofFloat(btnSub, "Rotation", 0, -360);
        ObjectAnimator subAlpha = ObjectAnimator.ofFloat(btnSub, "Alpha", 0, 1f);
        ObjectAnimator subScaleX = ObjectAnimator.ofFloat(btnSub, "ScaleX", 0.1f, 1f);
        ObjectAnimator subScaleY = ObjectAnimator.ofFloat(btnSub, "ScaleX", 0.1f, 1f);

        textNumber.clearAnimation();
        ObjectAnimator numberTranslation = ObjectAnimator.ofFloat(textNumber, "TranslationX", 0, (width - textNumber.getMeasuredWidth()) / -2f);
        ObjectAnimator numberRotation = ObjectAnimator.ofFloat(textNumber, "Rotation", 0, -360);
        ObjectAnimator numberAlpha = ObjectAnimator.ofFloat(textNumber, "Alpha", 0, 1f);
        ObjectAnimator numberScaleX = ObjectAnimator.ofFloat(textNumber, "ScaleX", 0.1f, 1f);
        ObjectAnimator numberScaleY = ObjectAnimator.ofFloat(textNumber, "ScaleX", 0.1f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(numberTranslation, numberRotation, numberAlpha, numberScaleX, numberScaleY,
                subTranslation, subRotation, subAlpha, subScaleX, subScaleY);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
    }

    /**
     * 关闭动画
     */
    private void closeAnimator() {
        if (!isShowing) {
            return;
        }
        isShowing = false;

        btnSub.clearAnimation();
        ObjectAnimator subTranslation = ObjectAnimator.ofFloat(btnSub, "TranslationX", (width - btnSub.getMeasuredWidth()) * -1f, 0);
        ObjectAnimator subRotation = ObjectAnimator.ofFloat(btnSub, "Rotation", 360, 0);
        ObjectAnimator subAlpha = ObjectAnimator.ofFloat(btnSub, "Alpha", 1f, 0);
        ObjectAnimator subScaleX = ObjectAnimator.ofFloat(btnSub, "ScaleX", 1f, 0.1f);
        ObjectAnimator subScaleY = ObjectAnimator.ofFloat(btnSub, "ScaleX", 1f, 0.1f);

        textNumber.clearAnimation();
        ObjectAnimator numberTranslation = ObjectAnimator.ofFloat(textNumber, "TranslationX", (width - textNumber.getMeasuredWidth()) / -2f, 0);
        ObjectAnimator numberRotation = ObjectAnimator.ofFloat(textNumber, "Rotation", 360, 0);
        ObjectAnimator numberAlpha = ObjectAnimator.ofFloat(textNumber, "Alpha", 1f, 0);
        ObjectAnimator numberScaleX = ObjectAnimator.ofFloat(textNumber, "ScaleX", 1f, 0.1f);
        ObjectAnimator numberScaleY = ObjectAnimator.ofFloat(textNumber, "ScaleX", 1f, 0.1f);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(numberTranslation, numberRotation, numberAlpha, numberScaleX, numberScaleY,
                subTranslation, subRotation, subAlpha, subScaleX, subScaleY);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textNumber.setText(String.valueOf(number));
                animatorSet.removeAllListeners();
            }
        });
    }

    public int getNumber() {
        return number;
    }

    private OnChangeListener listener;

    public OnChangeListener getListener() {
        return listener;
    }

    public void setListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public interface OnChangeListener {
        void onChange(boolean isAdd, int number);
    }

}
