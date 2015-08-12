package com.vcanpay.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

import com.example.vcanpay.R;

/**
 * Project Name:Vcanpay
 * File Name:${FILE_NAME}
 * Date:${Date}
 * Copyright (c) 2015, patrickwai.cn@hotmail.com All Rights Reserved.
 * <p/>
 * Created by patrick wai on 2015/8/10.
 */
public class LangFlagView extends ImageView implements Checkable{

    boolean mChecked;

    public LangFlagView(Context context) {
        this(context, null);
    }

    public LangFlagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LangFlagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mChecked) {
            Drawable rect = getResources().getDrawable(R.drawable.selector_lang_flag);
            rect.setBounds(0, 0, getWidth(), getHeight());
            rect.draw(canvas);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        invalidate();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
