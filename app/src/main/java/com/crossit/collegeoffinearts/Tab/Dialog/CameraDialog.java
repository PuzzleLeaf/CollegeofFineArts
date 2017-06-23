package com.crossit.collegeoffinearts.Tab.Dialog;

import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.crossit.collegeoffinearts.R;

/**
 * Created by cmtyx on 2017-06-22.
 */

public class CameraDialog extends Dialog {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_camera_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public CameraDialog(Context context) {
        super(context,R.style.theme_dialog);
    }
}
