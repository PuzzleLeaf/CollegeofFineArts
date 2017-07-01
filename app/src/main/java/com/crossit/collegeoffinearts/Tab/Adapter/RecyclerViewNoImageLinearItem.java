package com.crossit.collegeoffinearts.Tab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crossit.collegeoffinearts.R;

import java.util.ArrayList;

public class RecyclerViewNoImageLinearItem extends RecyclerView.Adapter<RecyclerViewNoImageLinearItem.ViewHolder> {

    private LayoutInflater mInflater;
    ArrayList<String> txt;
    public RecyclerViewNoImageLinearItem(Context context, ArrayList<String> txt)
    {
        this.mInflater = LayoutInflater.from(context);
        this.txt = txt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.board_linear_noimage_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.myTextView.setText(txt.get(position));
        holder.myBoardLike.setOnClickListener(new View.OnClickListener() {
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
        return txt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myTextView;
        public ImageView myBoardLike;
        public boolean boardLikeFlag = false;

        public ViewHolder(View itemView)
        {
            super(itemView);
            myTextView = (TextView)itemView.findViewById(R.id.board_txt);
            myBoardLike = (ImageView)itemView.findViewById(R.id.board_like);

        }


    }
}