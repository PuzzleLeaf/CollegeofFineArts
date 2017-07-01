package com.crossit.collegeoffinearts.Tab.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by cmtyx on 2017-06-22.
 */

public class CameraTest extends android.support.v7.widget.AppCompatTextView {

    public CameraTest(Context context)
    {
        super(context);
        init();
    }

    public CameraTest(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        init();
    }

    public CameraTest(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HANYGO240.ttf");
        setTypeface(tf);
    }
}
