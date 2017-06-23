package com.crossit.collegeoffinearts.Tab.board;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by cmtyx on 2017-06-22.
 */

public class FontTextView extends android.support.v7.widget.AppCompatTextView {

    public FontTextView(Context context)
    {
        super(context);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        init();
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr)
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
