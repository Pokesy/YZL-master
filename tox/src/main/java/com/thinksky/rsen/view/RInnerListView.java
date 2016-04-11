package com.thinksky.rsen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by angcyo on 16-03-20-020.
 */
public class RInnerListView extends ListView {
    public RInnerListView(Context context) {
        super(context);
    }

    public RInnerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST));
    }
}
