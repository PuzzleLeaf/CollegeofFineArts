package com.crossit.collegeoffinearts.Tab;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;

public class TabIcon {
    //커스텀 탭 아이콘 할당을 위한 클래스
    View view;
    ImageView imageView;

    public TabIcon(Context context, int resId)
    {
        view = LayoutInflater.from(context).inflate(R.layout.view_menu_tab,null);
        imageView = (ImageView) view.findViewById(R.id.tab);
        imageView.setImageResource(resId);

    }

    public void changeImage(int resId)
    {
        imageView.setImageResource(resId);
    }

    public View getView()
    {
        return view;
    }


}