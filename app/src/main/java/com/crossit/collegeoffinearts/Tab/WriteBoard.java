package com.crossit.collegeoffinearts.Tab;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.crossit.collegeoffinearts.myAuth;
import com.crossit.collegeoffinearts.myDataBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class WriteBoard extends AppCompatActivity {

    //테스트 리소스
    int tempRes[] = {R.drawable.samplea,R.drawable.sampleb,R.drawable.samplec,R.drawable.sampled,R.drawable.samplef};

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


    private TextView writeBoardName;
    private ImageView writeImageView;
    private EditText writeTitle;
    private EditText writeContents;
    private CameraDialog cameraDialog;
    private ImageView writeCommit;
    private TextView usedSelector;
    private boolean selectorFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_board);
        String temp = getIntent().getStringExtra("write");

        loading = new Loading(this);


        writeBoardName =(TextView)findViewById(R.id.write_board_name);
        writeTitle = (EditText)findViewById(R.id.write_title);
        writeContents = (EditText)findViewById(R.id.write_contents);
        writeImageView = (ImageView)findViewById(R.id.write_image);
        writeImageView.setDrawingCacheEnabled(true);
        writeImageView.buildDrawingCache();
        usedSelector = (TextView)findViewById(R.id.used_select);
        usedSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectorFlag)
                    usedSelector.setText("[삽니다]");
                else
                    usedSelector.setText("[팝니다]");

                selectorFlag=!selectorFlag;
            }
        });

        cameraDialog = new CameraDialog(this);

        setWriteBoardName(temp);


        myUser = myDataBase.database.getReference("유저");

        writeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraDialog.show();
                Random random = new Random();
                writeImageView.setImageResource(tempRes[random.nextInt(4)]);

            }
        });

        writeCommit = (ImageView) findViewById(R.id.write_commit);
        writeCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                setBoardRoot();
                Bitmap bitmap = writeImageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                tempKey = myRef.child("게시판").push().getKey();
                imagesRef = myDataBase.storageRef.child(tempKey);

                UploadTask uploadTask = imagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d("qwe",exception.getMessage());
                        Toast.makeText(getApplicationContext(),"네트워크 오류가 발생했습니다.",Toast.LENGTH_LONG).show();
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
                        imgFlag = true; //이미지 저장 완료
                        BoardObject boardObject =
                                new BoardObject(writeTitle.getText().toString(),writeContents.getText().toString(), myAuth.userId,myAuth.userEmail,myAuth.userName,tempKey,uri.toString());

                        myRef.child("게시판").child(tempKey).setValue(boardObject, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                contentsFlag = true;
                                complete();
                            }
                        });

                        myUser.child(myAuth.userId).child("작성글").push().setValue(tempKey, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                userFlag = true;
                                complete();


                            }
                        });
                    }
                });
            }
        });
    }

    void setBoardRoot()
    {
        //데이터베이서 경로 지정
        if(usedSelector.getVisibility() == View.GONE)
            myRef = myDataBase.database.getReference(writeBoardName.getText().toString());
        else {
            if(usedSelector.getText().toString().equals("[삽니다]"))
                myRef = myDataBase.database.getReference(writeBoardName.getText().toString()).child("삽니다");
            else
                myRef = myDataBase.database.getReference(writeBoardName.getText().toString()).child("팝니다");
        }
    }
    void complete()
    {
        if(imgFlag && contentsFlag && userFlag)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    loading.dismiss();
                }
            }).start();
            Toast.makeText(getApplicationContext(),"저장 완료",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    void setWriteBoardName(String name)
    {
        if(name.equals("0")) {
            writeBoardName.setText("중고");
            usedSelector.setVisibility(View.VISIBLE);
        }
        else if(name.equals("1"))
            writeBoardName.setText("전시");
        else if(name.equals("2"))
            writeBoardName.setText("구하기");
        else
            writeBoardName.setText("자유");
   }

    @Override
    protected void onPause() {
        super.onPause();
    }
}