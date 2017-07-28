package com.crossit.collegeoffinearts.Tab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crossit.collegeoffinearts.MyAuth;
import com.crossit.collegeoffinearts.MyDataBase;
import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Dialog.CameraDialog;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.Dialog.LoadingDialog;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;

public class ModifyBoard extends AppCompatActivity {

    //데이터베이스 변수
    private DatabaseReference myRef;
    private DatabaseReference myUser;
    StorageReference imagesRef;
    private String tempKey;

    boolean contentsFlag = false;

    private TextView writeBoardName;
    private ImageView writeImageView;
    private EditText writeTitle;
    private EditText writeContents;
    private ImageView writeCommit;
    private TextView writeImageCaption;

    BoardObject boardObject;
    String check = "";
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_board);
        check = getIntent().getStringExtra("check");
        key = getIntent().getStringExtra("key");

        init(); // 뷰 초기화 및 이벤트 등록
        dbInit();
        setWriteBoardName();


    }
    private void refreshData(){
        writeTitle.setText(boardObject.getTitle());
        writeContents.setText(boardObject.getContents());
    }

    void init() {
        writeBoardName = (TextView) findViewById(R.id.write_board_name);
        writeTitle = (EditText) findViewById(R.id.write_title);
        writeContents = (EditText) findViewById(R.id.write_contents);
        writeImageView = (ImageView) findViewById(R.id.write_image);
        writeImageView.setVisibility(View.GONE);
        writeImageCaption = (TextView)findViewById(R.id.write_image_caption);
        writeImageCaption.setVisibility(View.GONE);

        //등록 버튼
        writeCommit = (ImageView) findViewById(R.id.write_commit);
    }

    void dbInit() {
        myUser = MyDataBase.database.getReference("유저");

        if(check.equals("1")){
            myRef = MyDataBase.database.getReference("중고").child("삽니다").child("게시판").child(key);
        }
        else if(check.equals("2")) {
            myRef = MyDataBase.database.getReference("중고").child("팝니다").child("게시판").child(key);
        }
        else if(check.equals("3")) {
            myRef = MyDataBase.database.getReference("전시").child("게시판").child(key);
        }else if(check.equals("4")){
            myRef = MyDataBase.database.getReference("구하기").child("게시판").child(key);
        }else{
            myRef = MyDataBase.database.getReference("자유").child("게시판").child(key);
        }

        writeCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDateWrite();
            }
        });

        final ValueEventListener BoardObjectListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    boardObject = dataSnapshot.getValue(BoardObject.class);
                    refreshData();
                }
                else {
                    Toast.makeText(getApplicationContext(),"삭제된 게시물 입니다.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(BoardObjectListener);
    }


    void dbDateWrite()
    {
        LoadingDialog.loadingShow();
        boardObject.setTitle(writeTitle.getText().toString());
        boardObject.setContents(writeContents.getText().toString());

        myRef.setValue(boardObject, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                contentsFlag = true;
                complete();
            }
        });

    }


    void complete() {
            LoadingDialog.loadingDismiss();
            Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_LONG).show();
            finish();
    }

    void setWriteBoardName() {
            writeBoardName.setText("수정");
    }

}