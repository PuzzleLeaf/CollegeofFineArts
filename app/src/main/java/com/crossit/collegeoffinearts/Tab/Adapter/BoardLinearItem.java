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
import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;
import com.crossit.collegeoffinearts.MyDataBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class BoardLinearItem extends RecyclerView.Adapter<BoardLinearItem.ViewHolder> {


    private LayoutInflater mInflater;
    ArrayList<BoardObject> obj;
  // 1 = 삽니다 / 2= 팝니다 / 3= 전시
    //GlobalChecker.usedArticleFlag
    public BoardLinearItem(Context context, ArrayList<BoardObject> obj)
    {
        this.mInflater = LayoutInflater.from(context);
        this.obj = obj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.board_linear_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.myTextView.setText(obj.get(position).getTitle());
        holder.myBoardDate.setText(obj.get(position).getTime());
        holder.myBoardName.setText(obj.get(position).getUser_name());
        Glide.with(mInflater.getContext()).load(obj.get(position).getImage()).thumbnail(0.1f).into(holder.myImageView);
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
            if(GlobalChecker.usedArticleFlag  == 1)
                mRef = MyDataBase.database.getReference("중고").child("삽니다").child("게시판").child(obj.get(position).getBoard_id());
            else if(GlobalChecker.usedArticleFlag == 2)
                mRef = MyDataBase.database.getReference("중고").child("팝니다").child("게시판").child(obj.get(position).getBoard_id());
            else
                mRef = MyDataBase.database.getReference("전시").child("게시판").child(obj.get(position).getBoard_id());

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
                    Intent intent = new Intent(holder.context, BoardContentsActivity.class);
                    intent.putExtra("check",String.valueOf(GlobalChecker.usedArticleFlag ));
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Context context;
        public ImageView myImageView;
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
            myImageView =(ImageView)itemView.findViewById(R.id.board_img);
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