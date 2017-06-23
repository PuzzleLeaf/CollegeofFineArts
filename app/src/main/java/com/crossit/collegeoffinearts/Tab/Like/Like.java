package com.crossit.collegeoffinearts.Tab.Like;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Dialog.CameraDialog;
import com.crossit.collegeoffinearts.Tab.board.FontEditText;
import com.crossit.collegeoffinearts.Tab.board.RecyclerViewNoImageLinearItem;

import java.util.ArrayList;

public class Like extends Fragment {

    //RecyclerView
    private RecyclerViewNoImageLinearItem linearNoImgAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<String> txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.like_board, container, false);
        dataInit();
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

    //뷰 초기화 및 설정
    void recyclerViewInit(View view)
    {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearNoImgAdapter = new RecyclerViewNoImageLinearItem(getContext(), txt);

        recyclerView = (RecyclerView) view.findViewById(R.id.like_board);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(linearNoImgAdapter);
    }

}