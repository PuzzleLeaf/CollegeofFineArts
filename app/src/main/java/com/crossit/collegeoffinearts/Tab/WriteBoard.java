package com.crossit.collegeoffinearts.Tab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.Dialog.CameraDialog;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.MyAuth;
import com.crossit.collegeoffinearts.MyDataBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class WriteBoard extends AppCompatActivity {

    //데이터베이스 변수
    private DatabaseReference myRef;
    private DatabaseReference myUser;
    StorageReference imagesRef;
    private String tempKey;

    //로딩
    Loading loading;
    boolean imgFlag = false;
    boolean contentsFlag = false;
    boolean userFlag = false;


    private TextView writeImageCaption;
    private TextView writeBoardName;
    private ImageView writeImageView;
    private EditText writeTitle;
    private EditText writeContents;
    private ImageView writeCommit;
    private TextView usedSelector;
    private boolean selectorFlag = false;
    private ImageView cancel;

    //이미지 등록 플레그
    private boolean isImg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_board);
        String temp = getIntent().getStringExtra("write");

        loading = new Loading(this);

        init(temp); // 뷰 초기화 및 이벤트 등록
        dbInit();
        setWriteBoardName(temp);


    }

    void init(String temp) {
        writeBoardName = (TextView) findViewById(R.id.write_board_name);
        writeTitle = (EditText) findViewById(R.id.write_title);
        writeContents = (EditText) findViewById(R.id.write_contents);
        writeImageView = (ImageView) findViewById(R.id.write_image);
        writeImageView.setDrawingCacheEnabled(true);
        writeImageView.buildDrawingCache();
        writeImageCaption = (TextView) findViewById(R.id.write_image_caption);
        //이미지 등록 UI 업데이트
        if (temp.equals("2") || temp.equals("3")) {
            writeImageView.setVisibility(View.INVISIBLE);
            writeImageCaption.setVisibility(View.INVISIBLE);
        }
        cancel = (ImageView)findViewById(R.id.write_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        usedSelector = (TextView) findViewById(R.id.used_select);
        usedSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectorFlag)
                    usedSelector.setText("[삽니다]");
                else
                    usedSelector.setText("[팝니다]");

                selectorFlag = !selectorFlag;
            }
        });

        //등록 버튼
        writeCommit = (ImageView) findViewById(R.id.write_commit);
    }

    void dbInit() {
        myUser = MyDataBase.database.getReference("유저");

        //이미지 등록
        writeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CameraDialog.class);
                startActivityForResult(intent,100);

            }
        });

        writeCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                setBoardRoot();
                dataUpload();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;


        if(requestCode==100)
        {
            String imgUri = data.getStringExtra("image");
            Uri uri = Uri.parse(imgUri);

            writeImageView.setImageURI(uri);

            isImg = true;

        }
    }

    void dataUpload() {
        Bitmap bitmap = writeImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        tempKey = myRef.child("게시판").push().getKey();
        imagesRef = MyDataBase.storageRef.child(tempKey);

        //이미지 등록
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(), "네트워크 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                }).start();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri uri = taskSnapshot.getDownloadUrl();
                dbDateWrite(uri, taskSnapshot.getStorage().getName());
                imgFlag = true; //이미지 저장 완료

            }
        });
    }

    void dbDateWrite(Uri uri, String imageDir)
    {
        BoardObject boardObject;
        if(isImg)
            boardObject =  new BoardObject(writeTitle.getText().toString(), writeContents.getText().toString(), MyAuth.userId, MyAuth.userEmail, MyAuth.userName, tempKey, uri.toString(),imageDir);
        else
            boardObject =  new BoardObject(writeTitle.getText().toString(), writeContents.getText().toString(), MyAuth.userId, MyAuth.userEmail, MyAuth.userName, tempKey);

        //글 등록
        myRef.child("게시판").child(tempKey).setValue(boardObject, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                contentsFlag = true;
                complete();
            }
        });

        myUser.child(MyAuth.userId).child("작성글").child(tempKey).setValue(tempKey, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                userFlag = true;
                complete();
            }
        });
    }

    void setBoardRoot() {
        //데이터베이스 경로 지정
        if (usedSelector.getVisibility() == View.GONE)
            myRef = MyDataBase.database.getReference(writeBoardName.getText().toString());
        else {
            if (selectorFlag)
                myRef = MyDataBase.database.getReference(writeBoardName.getText().toString()).child("삽니다");
            else
                myRef = MyDataBase.database.getReference(writeBoardName.getText().toString()).child("팝니다");
        }
    }

    void complete() {
        if (imgFlag && contentsFlag && userFlag) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loading.dismiss();
                }
            }).start();
            Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    void setWriteBoardName(String name) {
        if (name.equals("0")) {
            writeBoardName.setText("중고");
            usedSelector.setVisibility(View.VISIBLE);
        } else if (name.equals("1"))
            writeBoardName.setText("전시");
        else if (name.equals("2"))
            writeBoardName.setText("구하기");
        else
            writeBoardName.setText("자유");
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}