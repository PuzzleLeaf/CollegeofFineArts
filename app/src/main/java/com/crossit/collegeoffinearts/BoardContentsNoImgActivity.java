package com.crossit.collegeoffinearts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crossit.collegeoffinearts.Tab.Adapter.CommentItem;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.ModifyBoard;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.Tab.models.CommentObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class BoardContentsNoImgActivity extends AppCompatActivity {


    private TextView title;
    private TextView date;
    private TextView name;
    private TextView count;
    private TextView contents;
    private TextView boardName;
    private TextView writeModify;
    private TextView writeRemove;



    //댓글
    private EditText commentEdit;
    private ImageView commentBtn;
    private RecyclerView commentView;
    private ArrayList<CommentObject> comments;
    private CommentItem commentAdapter;
    String commentKey;

    Loading loading;
    //넘겨 받은 값
    String key;
    String check;
    //DB
    DatabaseReference myRef;
    DatabaseReference myComment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_contents_no_img);

        check = getIntent().getStringExtra("check");
        key = getIntent().getStringExtra("board");

        viewInit();
        dbInit();
        commentCommit();
        loading = new Loading(this);




    }

    @Override
    protected void onStart() {
        super.onStart();

        final ValueEventListener BoardObjectListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    loading.show();
                }catch (Exception e)
                {

                }
                if(dataSnapshot.exists()) {
                    BoardObject boardObject = dataSnapshot.getValue(BoardObject.class);
                    title.setText(boardObject.getTitle());
                    date.setText(boardObject.getTime());
                    name.setText(boardObject.getUser_name()+"("+boardObject.getUser_email()+")");
                    count.setText(boardObject.getCount());
                    contents.setText(boardObject.getContents());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loading.dismiss();
                        }
                    }).start();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"게시물이 삭제되었습니다.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ValueEventListener CommentObjectListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comments.clear();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                try{
                    loading.show();
                }catch (Exception e)
                {

                }
                while(iterator.hasNext())
                {
                    CommentObject commentObject = iterator.next().getValue(CommentObject.class);
                    comments.add(commentObject);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                }).start();
                commentAdapter = new CommentItem(getApplicationContext(),comments,Integer.valueOf(check),key);
                commentView.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(BoardObjectListener);
        myComment.addValueEventListener(CommentObjectListener);
    }



    private void viewInit()
    {
        boardName = (TextView)findViewById(R.id.contents_board);
        title = (TextView)findViewById(R.id.contents_title);
        date = (TextView)findViewById(R.id.contents_date);
        name = (TextView)findViewById(R.id.contents_name);
        count = (TextView)findViewById(R.id.contents_count);
        contents = (TextView)findViewById(R.id.contents_main);
        writeModify = (TextView)findViewById(R.id.write_modify);
        //수정
        writeModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyBoard.class);
                intent.putExtra("check",check);
                intent.putExtra("key",key);
                startActivity(intent);
                finish();
            }
        });
        writeRemove = (TextView)findViewById(R.id.write_delete);
        writeRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.removeValue();
                myComment.removeValue();
                MyDataBase.database.getReference("유저").child(MyAuth.userId).child("작성글").child(key).removeValue();
                finish();
            }
        });

        //댓글
        comments = new ArrayList<>();
        commentBtn = (ImageView)findViewById(R.id.comment_commit);
        commentEdit = (EditText)findViewById(R.id.comment_edit);
        if(MyAuth.userId == null || MyAuth.userId.equals("non"))
        {
            commentBtn.setVisibility(View.GONE);
            commentEdit.setVisibility(View.GONE);
            writeRemove.setVisibility(View.GONE);
            writeModify.setVisibility(View.GONE);
        }
        commentView = (RecyclerView)findViewById(R.id.comment_view);
        commentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void dbInit()
    {
        if(check.equals("4")) {
            myRef = MyDataBase.database.getReference("구하기").child("게시판").child(key);
            myComment = MyDataBase.database.getReference("구하기").child("댓글").child(key);
            boardName.setText("구하기");
        }
        else {
            myRef = MyDataBase.database.getReference("자유").child("게시판").child(key);
            myComment = MyDataBase.database.getReference("자유").child("댓글").child(key);
            boardName.setText("자유");
        }

    }
    //댓글 작성
    private void commentCommit()
    {
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentKey = myComment.push().getKey();
                CommentObject commentObject = new CommentObject(commentEdit.getText().toString(), MyAuth.userId, MyAuth.userName+"("+ MyAuth.userEmail+")",commentKey);
                commentEdit.setText("");
                myComment.child(commentKey).setValue(commentObject, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });
            }
        });
    }

}
