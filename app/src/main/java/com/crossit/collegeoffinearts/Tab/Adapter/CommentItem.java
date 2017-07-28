package com.crossit.collegeoffinearts.Tab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.models.CommentObject;
import com.crossit.collegeoffinearts.MyAuth;
import com.crossit.collegeoffinearts.MyDataBase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class CommentItem extends RecyclerView.Adapter<CommentItem.ViewHolder> {

    private LayoutInflater mInflater;
    ArrayList<CommentObject> obj;
    int commentCheck; // 1 = 삽니다 / 2= 팝니다 / 3= 전시
    String boardId;
    public CommentItem(Context context, ArrayList<CommentObject> obj, int commentCheck, String boardId)
    {
        this.mInflater = LayoutInflater.from(context);
        this.obj = obj;
        this.commentCheck = commentCheck;
        this.boardId =boardId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.board_comment_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.comment_contents.setText(obj.get(position).getContents());
        holder.comment_date.setText(obj.get(position).getTime());
        holder.comment_name.setText(obj.get(position).getUser_name());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComment(position);
            }
        });

        if(MyAuth.userId !=null && obj.get(position).getUser_id().equals(MyAuth.userId))
            holder.delete.setVisibility(View.VISIBLE);
        else
            holder.delete.setVisibility(View.GONE);

    }

    private void deleteComment(int position)
    {
        DatabaseReference mRef;
        if(commentCheck == 1)
            mRef = MyDataBase.database.getReference("중고").child("삽니다").child("댓글").child(boardId).child(obj.get(position).getComment_id());
        else if(commentCheck == 2)
            mRef = MyDataBase.database.getReference("중고").child("팝니다").child("댓글").child(boardId).child(obj.get(position).getComment_id());
        else if(commentCheck == 3)
            mRef = MyDataBase.database.getReference("전시").child("댓글").child(boardId).child(obj.get(position).getComment_id());
        else if(commentCheck == 4)
            mRef = MyDataBase.database.getReference("구하기").child("댓글").child(boardId).child(obj.get(position).getComment_id());
        else
            mRef = MyDataBase.database.getReference("자유").child("댓글").child(boardId).child(obj.get(position).getComment_id());
        mRef.setValue(null);
    }


    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView comment_contents;
        public TextView comment_name;
        public TextView comment_date;
        public TextView delete;

        public ViewHolder(View itemView)
        {
            super(itemView);
            comment_contents = (TextView)itemView.findViewById(R.id.comment_contents);
            comment_name = (TextView)itemView.findViewById(R.id.comment_name);
            comment_date = (TextView)itemView.findViewById(R.id.comment_date);
            delete = (TextView)itemView.findViewById(R.id.comment_delete);
        }


    }
}