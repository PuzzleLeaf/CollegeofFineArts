package com.crossit.collegeoffinearts.Tab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.Tab.models.BoardObject;

import java.util.ArrayList;

public class BoardGridItem extends RecyclerView.Adapter<BoardGridItem.ViewHolder> {

    private LayoutInflater mInflater;
    ArrayList<BoardObject> obj;

    public BoardGridItem(Context context, ArrayList<BoardObject> obj)
    {
        this.mInflater = LayoutInflater.from(context);
        this.obj = obj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.board_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.myTextView.setText(obj.get(position).getTitle());
        Glide.with(mInflater.getContext()).load(obj.get(position).getImage()).into(holder.myImageView);
        holder.myBoardDate.setText(obj.get(position).getTime());
        holder.myBoardName.setText(obj.get(position).getUser_name());
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
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView myImageView;
        public TextView myTextView;
        public ImageView myBoardLike;
        public TextView myBoardDate;
        public TextView myBoardName;
        public LinearLayout myLikeOuter;
        public boolean boardLikeFlag = false;
        public LinearLayout myBoardFrame;

        public ViewHolder(View itemView)
        {
            super(itemView);
            myImageView =(ImageView)itemView.findViewById(R.id.board_img);
            myTextView = (TextView)itemView.findViewById(R.id.board_txt);
            myBoardLike = (ImageView)itemView.findViewById(R.id.board_like);
            myLikeOuter = (LinearLayout)itemView.findViewById(R.id.board_like_outer);
            myBoardFrame = (LinearLayout)itemView.findViewById(R.id.board_frame);
            myBoardDate = (TextView)itemView.findViewById(R.id.board_date);
            myBoardName = (TextView)itemView.findViewById(R.id.board_name);

        }


    }
}