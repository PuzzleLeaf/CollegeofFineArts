package com.crossit.collegeoffinearts.Tab.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crossit.collegeoffinearts.BoardContentsActivity;
import com.crossit.collegeoffinearts.BoardContentsNoImgActivity;
import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.myDataBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class BoardLinearNoImgItem extends RecyclerView.Adapter<BoardLinearNoImgItem.ViewHolder> {


    private LayoutInflater mInflater;
    ArrayList<BoardObject> obj;
    int boardCheck; // 4 = 구하기 / 5= 자유

    public BoardLinearNoImgItem(Context context, ArrayList<BoardObject> obj,int boardCheck)
    {
        this.mInflater = LayoutInflater.from(context);
        this.obj = obj;
        this.boardCheck =boardCheck;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.board_linear_noimage_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.myTextView.setText(obj.get(position).getTitle());
        holder.myBoardDate.setText(obj.get(position).getTime());
        holder.myBoardName.setText(obj.get(position).getUser_name());
        holder.myBoardCount.setText(obj.get(position).getCount());
        holder.myLikeOuter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.boardLikeFlag)
                {
                    holder.myBoardLike.setImageResource(R.drawable.like_p);
                }
                else
                {
                    holder.myBoardLike.setImageResource(R.drawable.like);
                }
                holder.boardLikeFlag = !holder.boardLikeFlag;
            }
        });

        holder.myBoardFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countInc(position,holder);
            }
        });

    }

    public void countInc(final int position, final ViewHolder holder)
    {
        try
        {
            DatabaseReference mRef;
            if (boardCheck == 4)
                mRef = myDataBase.database.getReference("구하기").child("게시판").child(obj.get(position).getBoard_id());
            else
                mRef = myDataBase.database.getReference("자유").child("게시판").child(obj.get(position).getBoard_id());

            mRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    BoardObject boardObject = mutableData.getValue(BoardObject.class);
                    int count = Integer.parseInt(boardObject.getCount())+1;
                    boardObject.setCount(String.valueOf(count));
                    mutableData.setValue(boardObject);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                    Intent intent = new Intent(holder.context, BoardContentsNoImgActivity.class);
                    intent.putExtra("check",String.valueOf(boardCheck));
                    intent.putExtra("board",obj.get(position).getBoard_id());
                    holder.context.startActivity(intent);
                }
            });
        }
        catch (IndexOutOfBoundsException ie)
        {
            Log.d("qwe","Error");
        }
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Context context;
        public TextView myTextView;
        public ImageView myBoardLike;
        public TextView myBoardDate;
        public TextView myBoardName;
        public boolean boardLikeFlag = false;
        public LinearLayout myLikeOuter;
        public TextView myBoardCount;
        public LinearLayout myBoardFrame;

        public ViewHolder(View itemView)
        {
            super(itemView);
            context = itemView.getContext();
            myTextView = (TextView)itemView.findViewById(R.id.board_txt);
            myBoardLike = (ImageView)itemView.findViewById(R.id.board_like);
            myLikeOuter = (LinearLayout)itemView.findViewById(R.id.board_like_outer);
            myBoardDate = (TextView)itemView.findViewById(R.id.board_date);
            myBoardName = (TextView)itemView.findViewById(R.id.board_name);
            myBoardCount = (TextView)itemView.findViewById(R.id.board_count);
            myBoardFrame = (LinearLayout)itemView.findViewById(R.id.board_frame);

        }


    }
}