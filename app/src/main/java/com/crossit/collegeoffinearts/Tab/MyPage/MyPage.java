package com.crossit.collegeoffinearts.Tab.MyPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Dialog.LoginDialog;
import com.crossit.collegeoffinearts.Tab.board.RecyclerViewNoImageLinearItem;

import java.util.ArrayList;

public class MyPage extends Fragment {

    //RecyclerView
    private RecyclerViewNoImageLinearItem linearNoImgAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<String> txt;

    LoginDialog loginDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage, container, false);
        loginDialog = new LoginDialog(getContext());
        dataInit();
        settingBtnInit(view);
        recyclerViewInit(view);
        return view;
    }

    //recyclerViewInit 보다 먼저 나와야 함
    void dataInit()
    {
        txt = new ArrayList<>();
        txt.add("길에서 길을 만나다");
        txt.add("Smart Exhibition");
        txt.add("같다? 같다!");
        txt.add("Home table deco fair 2013");
        txt.add("ART MARKET");
        txt.add("Star Wars");
        txt.add("길에서 길을 만나다");
    }

    void settingBtnInit(View view)
    {
        ImageView setting = (ImageView)view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.show();
            }
        });
    }
    //뷰 초기화 및 설정
    void recyclerViewInit(View view)
    {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearNoImgAdapter = new RecyclerViewNoImageLinearItem(getContext(), txt);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_board);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(linearNoImgAdapter);
    }

}