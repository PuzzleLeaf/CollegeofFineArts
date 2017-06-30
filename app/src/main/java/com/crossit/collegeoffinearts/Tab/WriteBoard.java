package com.crossit.collegeoffinearts.Tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.CustomView.FontEditText;
import com.crossit.collegeoffinearts.Tab.Dialog.CameraDialog;

public class WriteBoard extends AppCompatActivity {

    private TextView writeBoardName;
    private ImageView writeImageView;
    private FontEditText writeTitle;
    private CameraDialog cameraDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_board);
        String temp = getIntent().getStringExtra("write");

        writeBoardName =(TextView)findViewById(R.id.write_board_name);
        writeTitle = (FontEditText)findViewById(R.id.write_title);
        writeImageView = (ImageView)findViewById(R.id.write_image);

        cameraDialog = new CameraDialog(this);

        setWriteBoardName(temp);

        writeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog.show();
            }
        });
    }

    void setWriteBoardName(String name)
    {
        if(name.equals("0"))
            writeBoardName.setText("팝니다");
        else if(name.equals("1"))
            writeBoardName.setText("전시");
        else if(name.equals("2"))
            writeBoardName.setText("구하기");
        else
            writeBoardName.setText("자유");
   }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}