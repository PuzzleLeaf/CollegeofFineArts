package com.crossit.collegeoffinearts;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.crossit.collegeoffinearts.Tab.Adapter.BoardGridItem;
import com.crossit.collegeoffinearts.Tab.Adapter.BoardGridSearchItem;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.Dialog.LoadingDialog;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;


public class SearchActivity extends AppCompatActivity {

    private BoardGridSearchItem gridAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ArrayList<BoardObject> boardObj;
    private RecyclerView recyclerView;

    private EditText searchEdit;
    private String query = "";

    int searchCount = 1;


    DatabaseReference myRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_board);
        LoadingDialog.loading = new Loading(this);

        init();
        recyclerInit();

    }

    private void init(){
        boardObj = new ArrayList<>();
        searchEdit = (EditText)findViewById(R.id.search);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    boardObj.clear();
                    query = searchEdit.getText().toString();
                    LoadingDialog.loadingShow();
                    searchStart();
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void recyclerInit(){
        recyclerView = (RecyclerView)findViewById(R.id.search_board);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        gridAdapter = new BoardGridSearchItem(this, boardObj);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(gridAdapter);
    }

    void searchStart(){



        searchCount = 1;
        myRef = MyDataBase.database.getReference("중고").child("삽니다").child("게시판");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext())
                {
                    BoardObject boardObject = iterator.next().getValue(BoardObject.class);
                    if(boardObject.getTitle().contains(query)) {
                        boardObject.setTempChecker(searchCount);
                        boardObj.add(0, boardObject);
                    }
                }
                searchIng();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void searchIng()
    {
        searchCount++;
        if(searchCount==2){
            myRef = MyDataBase.database.getReference("중고").child("팝니다").child("게시판");
        }else if(searchCount == 3){
            myRef = MyDataBase.database.getReference("전시").child("게시판");
        }else if(searchCount == 4){
            myRef = MyDataBase.database.getReference("구하기").child("게시판");
        }else{
            myRef = MyDataBase.database.getReference("자유").child("게시판");
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext())
                {
                    BoardObject boardObject = iterator.next().getValue(BoardObject.class);
                    if(boardObject.getTitle().contains(query)) {
                        boardObject.setTempChecker(searchCount);
                        boardObj.add(0, boardObject);
                    }
                }
                if(searchCount == 5){
                    loadView();
                }else{
                    searchIng();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void loadView() {

        gridAdapter.notifyDataSetChanged();
        gridAdapter.notifyItemRangeChanged(0,gridAdapter.getItemCount());

        LoadingDialog.loadingDismiss();

    }
}
