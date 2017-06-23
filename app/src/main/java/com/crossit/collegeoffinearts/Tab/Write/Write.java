package com.crossit.collegeoffinearts.Tab.Write;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Dialog.CameraDialog;
import com.crossit.collegeoffinearts.Tab.board.FontEditText;

public class Write extends Fragment {

    private TextView writeBoardName;
    private ImageView writeImageView;
    private FontEditText writeTitle;

    private CameraDialog cameraDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.write_board, container, false);
        writeBoardName =(TextView)view.findViewById(R.id.write_board_name);
        writeTitle = (FontEditText)view.findViewById(R.id.write_title);
        writeImageView = (ImageView)view.findViewById(R.id.write_image);

        cameraDialog = new CameraDialog(getContext());
        String temp = getArguments().getString("send");
        setWriteBoardName(temp);

        writeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog.show();
            }
        });

        Log.d("qwe",temp);
        return view;
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
}