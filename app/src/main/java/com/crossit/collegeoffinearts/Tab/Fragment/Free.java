package com.crossit.collegeoffinearts.Tab.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.crossit.collegeoffinearts.Tab.Adapter.BoardLinearNoImgItem;
import com.crossit.collegeoffinearts.Tab.Dialog.LoadingDialog;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.MyDataBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Free extends Fragment {
    View view;
    //swipe
    SwipeRefreshLayout swipeRefreshLayout;

    //RecyclerView
    private BoardLinearNoImgItem linearNoImgAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<BoardObject> boardObj;

    //Spinner 변수 모음
    private Spinner spinner;
    private ArrayAdapter<CharSequence> option;
    private TextView option_text;

    //데이터 베이스
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.free_board, container, false);
        boardObj = new ArrayList<>();
        spinnerInit(view);
        recyclerViewInit(view);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.used_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataInit(view);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataInit(view);
    }

    //recyclerViewInit 보다 먼저 나와야 함
    void dataInit(View view) {
        LoadingDialog.loadingShow();
        myRef = MyDataBase.database.getReference("자유").child("게시판");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                boardObj.clear();
                while(iterator.hasNext())
                {
                    BoardObject boardObject = iterator.next().getValue(BoardObject.class);
                    boardObj.add(0,boardObject);
                }
//                LoadingCtrl.thread.start();
                loadView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //정렬 메뉴 초기화
    void spinnerInit(View view) {
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
    void recyclerViewInit(View view) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearNoImgAdapter = new BoardLinearNoImgItem(getContext(), boardObj,5);

        recyclerView = (RecyclerView) view.findViewById(R.id.free_board);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(linearNoImgAdapter);
    }

    void loadView() {
        linearNoImgAdapter.notifyDataSetChanged();
        LoadingDialog.loadingDismiss();
    }
}