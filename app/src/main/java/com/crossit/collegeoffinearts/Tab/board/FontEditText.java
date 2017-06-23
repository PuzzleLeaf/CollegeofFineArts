package com.crossit.collegeoffinearts.Tab.board;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by cmtyx on 2017-06-22.
 */

public class FontEditText extends android.support.v7.widget.AppCompatEditText {
    public FontEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context,attrs,defStyle);
        init();
    }

    public FontEditText(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        init();
    }

    public FontEditText(Context context)
    {
        super(context);
        init();
    }

    public void init()
    {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "HANYGO240.ttf");
            setTypeface(tf);
        }
    }
}
