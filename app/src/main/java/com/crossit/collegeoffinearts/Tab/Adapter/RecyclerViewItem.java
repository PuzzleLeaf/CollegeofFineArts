package com.crossit.collegeoffinearts.Tab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;

import java.util.ArrayList;

public class RecyclerViewItem extends RecyclerView.Adapter<RecyclerViewItem.ViewHolder> {

    private LayoutInflater mInflater;
    ArrayList<Integer> resId;
    ArrayList<String> txt;

    public RecyclerViewItem(Context context, ArrayList<Integer> resId, ArrayList<String> txt)
    {
        this.mInflater = LayoutInflater.from(context);
        this.resId = resId;
        this.txt = txt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.board_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.myTextView.setText(txt.get(position));
        holder.myImageView.setImageResource(resId.get(position));
        holder.myBoardFrame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!holder.boardLikeFlag)
                {
                    holder.myBoardLike.setImageResource(R.drawable.like_p);
                }
                else
                {
                    holder.myBoardLike.setImageResource(R.drawable.like);
                }
                holder.boardLikeFlag = !holder.boardLikeFlag;
                return true;
            }
        });

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
        return resId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView myImageView;
        public TextView myTextView;
        public ImageView myBoardLike;
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

        }


    }
}