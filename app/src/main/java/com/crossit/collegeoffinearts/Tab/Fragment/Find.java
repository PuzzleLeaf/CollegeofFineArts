package com.crossit.collegeoffinearts.Tab.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Adapter.RecyclerViewNoImageLinearItem;

import java.util.ArrayList;

public class Find extends Fragment {

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
        View view = inflater.inflate(R.layout.find_board, container, false);
        dataInit();
        spinnerInit(view);
        recyclerViewInit(view);
        return view;
    }

    //recyclerViewInit 보다 먼저 나와야 함
    void dataInit() {
        txt = new ArrayList<>();

        txt.add("OOO에서 같이 작업하실분 구해요");
        txt.add("아르바이트 모집!!");
        txt.add("9해요");
        txt.add("공모전 같이 하실분?");
        txt.add("모여라 미대생!!");
        txt.add("아무거나 구해요");


    }

    //정렬 메뉴 초기화
    void spinnerInit(View view) {
        //Spinner Start
        spinner = (Spinner) view.findViewById(R.id.find_board_spinner);
        option_text = (TextView) view.findViewById(R.id.find_board_spinner_text);
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
    void recyclerViewInit(View view) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearNoImgAdapter = new RecyclerViewNoImageLinearItem(getContext(), txt);

        recyclerView = (RecyclerView) view.findViewById(R.id.find_board);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(linearNoImgAdapter);
    }

}