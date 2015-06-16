package com.vcanpay.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

import com.example.vcanpay.R;
import com.vcanpay.activity.FuncItemFragment;

/**
 * Created by patrick wai on 2015/6/15.
 */
public class AutoMeasureGridView extends GridView {

    public AutoMeasureGridView(Context context) {
        super(context);
    }

    public AutoMeasureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoMeasureGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoMeasureGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed) {
            FuncItemFragment.CustomAdapter adapter = (FuncItemFragment.CustomAdapter)getAdapter();

            int numColumns = getContext().getResources().getInteger(R.integer.list_num_columns);
            GridViewItemLayout.initItemLayout(numColumns, adapter.getCount());

            if(numColumns > 1) {
                int columnWidth = getMeasuredWidth() / numColumns;
                adapter.measureItems(columnWidth);
            }
        }
        super.onLayout(changed, l, t, r, b);
    }
}