package com.crossit.collegeoffinearts.Tab.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.crossit.collegeoffinearts.Tab.Adapter.BoardGridItem;
import com.crossit.collegeoffinearts.Tab.Adapter.BoardLinearItem;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.Dialog.LoadingDialog;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.MyDataBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Exhibition extends Fragment implements AdapterView.OnItemSelectedListener {

    View view;
    //swipe
    SwipeRefreshLayout swipeRefreshLayout;
    //RecyclerView
    private BoardGridItem gridAdapter;
    private BoardLinearItem linearAdapter;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private RecyclerView recyclerView;

    private ArrayList<BoardObject> boardObj;

    //Spinner 변수 모음
    private Spinner spinner;
    private ArrayAdapter<CharSequence> option;
    private TextView option_text;

    //게시판 정렬 변경
    private ImageView changeView;
    private boolean changeFlag = false;

    //데이터 베이스
    DatabaseReference myRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exhibition_board, container, false);
        boardObj = new ArrayList<>();


        recyclerViewInit(view);
        spinnerInit(view);
        changeViewInit(view);


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
    void dataInit(View view)
    {
        LoadingDialog.loadingShow();
        myRef = MyDataBase.database.getReference("전시").child("게시판");

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
    void spinnerInit(View view)
    {
        //Spinner Start
        spinner = (Spinner)view.findViewById(R.id.exhibit_board_spinner);
        option_text = (TextView)view.findViewById(R.id.exhibit_board_spinner_text);
        option_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
        option = ArrayAdapter.createFromResource(getContext(),
                R.array.option, android.R.layout.simple_spinner_item);
        option.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(option);
        //Spinner End
    }

    //뷰 초기화 및 설정
    void recyclerViewInit(View view)
    {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.exhibit_board);
        gridAdapter = new BoardGridItem(getContext(), boardObj);
        linearAdapter = new BoardLinearItem(getContext(),boardObj);

        if(!changeFlag)
        {
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(gridAdapter);
        }
        else
        {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(linearAdapter);
        }


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

    void loadView()
    {
        if(changeFlag)
        {
            linearAdapter.notifyDataSetChanged();
        }
        else
        {
            gridAdapter.notifyDataSetChanged();
            gridAdapter.notifyItemRangeChanged(0,gridAdapter.getItemCount());
        }
        LoadingDialog.loadingDismiss();

    }
    //spinner 선택 이벤트 구현
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        option_text.setText(option.getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}