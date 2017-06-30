package com.crossit.collegeoffinearts.Tab.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Adapter.RecyclerViewItem;
import com.crossit.collegeoffinearts.Tab.Adapter.RecyclerViewLinearItem;

import java.util.ArrayList;

public class UsedArticle extends Fragment {

    //RecyclerView
    private RecyclerViewItem gridAdapter;
    private RecyclerViewLinearItem linearAdapter;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView recyclerView;

    private ArrayList<Integer> resId;
    private ArrayList<String> txt;

    //Spinner 변수 모음
    private Spinner spinner;
    private ArrayAdapter<CharSequence> option;
    private TextView option_text;

    //게시판 정렬 변경
    private ImageView changeView;
    private boolean changeFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.used_board, container, false);

        dataInit();
        spinnerInit(view);
        changeViewInit(view);
        recyclerViewInit(view);

        return view;
    }

    //recyclerViewInit 보다 먼저 나와야 함
    void dataInit()
    {
        resId = new ArrayList<>();
        txt = new ArrayList<>();
        for(int i=0;i<3;i++) {
            resId.add(R.drawable.u_sample);
            txt.add("붓 4종세트 싸게 팔아요");
            resId.add(R.drawable.u_sample2);
            txt.add("나이프 팔아요!!");
            resId.add(R.drawable.u_sample3);
            txt.add("최저가!! 보고가세요");
            resId.add(R.drawable.u_sample4);
            txt.add("이젤 판매합니다[급처]");
            resId.add(R.drawable.u_sample5);
            txt.add("색연필 사실분?");
            resId.add(R.drawable.u_sample6);
            txt.add("그때 그 물통");
        }

    }

    //정렬 메뉴 초기화
    void spinnerInit(View view)
    {
        //Spinner Start
        spinner = (Spinner)view.findViewById(R.id.used_board_spinner);
        option_text = (TextView)view.findViewById(R.id.used_board_spinner_text);
        option_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
        option = ArrayAdapter.createFromResource(getContext(),
                R.array.option, android.R.layout.simple_spinner_item);
        option.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option_text.setText(option.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(option);
        //Spinner End
    }

    //뷰 초기화 및 설정
    void recyclerViewInit(View view)
    {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearAdapter = new RecyclerViewLinearItem(getContext(), resId,txt);
        gridAdapter = new RecyclerViewItem(getContext(), resId,txt);

        recyclerView = (RecyclerView) view.findViewById(R.id.used_board);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(gridAdapter);
    }

    void changeViewInit(View view)
    {
        changeView = (ImageView)view.findViewById(R.id.view_change);
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!changeFlag) {
                    changeView.setImageResource(R.drawable.board_align_p);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(linearAdapter);
                }
                else
                {
                    changeView.setImageResource(R.drawable.board_align);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    recyclerView.setAdapter(gridAdapter);
                }
                changeFlag = !changeFlag;
            }
        });
    }

}