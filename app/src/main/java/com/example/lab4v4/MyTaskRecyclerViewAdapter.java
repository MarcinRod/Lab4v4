package com.example.lab4v4;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4v4.dummy.TasksContent.TaskItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TaskItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {
    public interface InputEventsListener {
        public void onClickEvent(int position);
        public void onLongClickEvent(int position);
    }
    private InputEventsListener mListener;
    private final List<TaskItem> mValues;

    public MyTaskRecyclerViewAdapter(List<TaskItem> items) {
        mValues = items;
    }
    public void setInputEventListener(InputEventsListener listener){
        mListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).title);
        String picPath = holder.mItem.picPath;
        Context context = holder.mView.getContext();
        if(picPath != null && !picPath.isEmpty() ){
            Drawable taskDrawable;

            switch(picPath){
                case "Drawable 2":taskDrawable = context.getDrawable(R.drawable.circle_drawable_orange);break;
                case "Drawable 3":taskDrawable = context.getDrawable(R.drawable.circle_drawable_red);break;
                case "Drawable 1":
                default:
                    taskDrawable = context.getDrawable(R.drawable.circle_drawable_green);
            }
            holder.mImageView.setImageDrawable(taskDrawable);
        }
        else
        {
            holder.mImageView.setImageDrawable(context.getDrawable(R.drawable.circle_drawable_green));
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!= null){
                    mListener.onClickEvent(position);
                }
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mListener != null)
                    mListener.onLongClickEvent(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public TaskItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.itemContent);
            mImageView = view.findViewById(R.id.itemImageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}