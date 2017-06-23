package com.crossit.collegeoffinearts.Tab.Free;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.board.RecyclerViewLinearItem;
import com.crossit.collegeoffinearts.Tab.board.RecyclerViewNoImageLinearItem;

import java.util.ArrayList;

public class Free extends Fragment {
    //RecyclerView
    private RecyclerViewNoImageLinearItem linearNoImgAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<String> txt;

    //Spinner 변수 모음
    private Spinner spinner;
    private ArrayAdapter<CharSequence> option;
    private TextView option_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.free_board, container, false);
        dataInit();
        spinnerInit(view);
        recyclerViewInit(view);
        return view;
    }

    //recyclerViewInit 보다 먼저 나와야 함
    void dataInit()
    {
        txt = new ArrayList<>();
        txt.add("자유게시판");
        txt.add("자");
        txt.add("유");
        txt.add("게");
        txt.add("시");
        txt.add("판");
        txt.add("자유게시판 입니다.");
    }

    //정렬 메뉴 초기화
    void spinnerInit(View view)
    {
        //Spinner Start
        spinner = (Spinner)view.findViewById(R.id.free_board_spinner);
        option_text = (TextView)view.findViewById(R.id.free_board_spinner_text);
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
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearNoImgAdapter = new RecyclerViewNoImageLinearItem(getContext(), txt);

        recyclerView = (RecyclerView) view.findViewById(R.id.free_board);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(linearNoImgAdapter);
    }
}