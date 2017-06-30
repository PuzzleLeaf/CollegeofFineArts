package com.crossit.collegeoffinearts.Tab.Dialog;

import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

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

        externalClick();
        cameraClick();
        galleryClick();

    }

    public void externalClick()
    {
        LinearLayout external = (LinearLayout)findViewById(R.id.camera_dialog);
        external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void cameraClick()
    {
        LinearLayout cameraClick = (LinearLayout)findViewById(R.id.camera_select);
        cameraClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void galleryClick()
    {
        LinearLayout galleryClick = (LinearLayout)findViewById(R.id.gallery_select);
        galleryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public CameraDialog(Context context) {
        super(context,R.style.theme_dialog);
    }
}
