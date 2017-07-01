package com.crossit.collegeoffinearts;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crossit.collegeoffinearts.Tab.Adapter.CommentItem;
import com.crossit.collegeoffinearts.Tab.Dialog.Loading;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.Tab.models.CommentObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class BoardContentsActivity extends AppCompatActivity {


    private TextView title;
    private TextView date;
    private TextView name;
    private TextView count;
    private TextView contents;
    private ImageView img;
    private TextView boardName;


    //댓글
    private EditText commentEdit;
    private ImageView commentBtn;
    private RecyclerView commentView;
    private ArrayList<CommentObject> comments;
    private CommentItem commentAdapter;
    String commentKey;

    Loading loading;
    //넘겨 받은 값
    String check;
    String key;
    //DB
    DatabaseReference myRef;
    DatabaseReference myComment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_contents);

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
                    Glide.with(getApplicationContext()).load(boardObject.getImage()).into(img);
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
                    comments.add(0,commentObject);
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
        img = (ImageView)findViewById(R.id.contents_image);

        //댓글
        comments = new ArrayList<>();
        commentBtn = (ImageView)findViewById(R.id.comment_commit);
        commentEdit = (EditText)findViewById(R.id.comment_edit);
        if(myAuth.userId == null || myAuth.userId.equals("non"))
        {
            commentBtn.setVisibility(View.GONE);
            commentEdit.setVisibility(View.GONE);
        }
        commentView = (RecyclerView)findViewById(R.id.comment_view);
        commentView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void dbInit()
    {
        if(check.equals("1"))//삽니다.
        {
            myRef = myDataBase.database.getReference("중고").child("삽니다").child("게시판").child(key);
            myComment = myDataBase.database.getReference("중고").child("삽니다").child("댓글").child(key);
            boardName.setText("삽니다");
        }
        else if(check.equals("2")) {
            myRef = myDataBase.database.getReference("중고").child("팝니다").child("게시판").child(key);
            myComment = myDataBase.database.getReference("중고").child("팝니다").child("댓글").child(key);
            boardName.setText("팝니다");
        }
        else {
            myRef = myDataBase.database.getReference("전시").child("게시판").child(key);
            myComment = myDataBase.database.getReference("전시").child("댓글").child(key);
            boardName.setText("전시");
        }
    }
    //댓글 작성
    private void commentCommit()
    {
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentKey = myComment.push().getKey();
                CommentObject commentObject = new CommentObject(commentEdit.getText().toString(),myAuth.userId,myAuth.userName+"("+myAuth.userEmail+")",commentKey);
                myComment.child(commentKey).setValue(commentObject, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });
            }
        });
    }

}
