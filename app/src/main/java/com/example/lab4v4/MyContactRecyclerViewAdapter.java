package com.example.lab4v4;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab4v4.dummy.ContactsContent.ContactItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ContactItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {
    public interface InputEventsListener {
        public void onClickEvent(int position);
        public void onLongClickEvent(int position);
        public void onBinClickEvent(int position);
    }
    private InputEventsListener mListener;
    private final List<ContactItem> mValues;

    public MyContactRecyclerViewAdapter(List<ContactItem> items) {
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
        holder.mContentView.setText(mValues.get(position).toString());
        String picPath = holder.mItem.picPath;
        Context context = holder.mView.getContext();
        if(picPath != null && !picPath.isEmpty() ){
            if(!picPath.contains("JPEG")) {
                Drawable taskDrawable;

                switch(picPath) {
                    case "avatar 1":
                        taskDrawable = context.getDrawable(R.drawable.avatar_1);
                        break;
                    case "avatar 2":
                        taskDrawable = context.getDrawable(R.drawable.avatar_2);
                        break;
                    case "avatar 3":
                        taskDrawable = context.getDrawable(R.drawable.avatar_3);
                        break;
                    case "avatar 4":
                        taskDrawable = context.getDrawable(R.drawable.avatar_4);
                        break;
                    case "avatar 5":
                    default:
                        taskDrawable = context.getDrawable(R.drawable.avatar_5);
                }
                holder.mImageView.setImageDrawable(taskDrawable);
            }else
            {
                Bitmap bitmap = PicUtils.decodePic(picPath, 100, 100);
                holder.mImageView.setImageBitmap(bitmap);
            }
        }
        else
        {
            holder.mImageView.setImageDrawable(context.getDrawable(R.drawable.avatar_5));
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
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                    mListener.onBinClickEvent(position);
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
        public final ImageButton mImageButton;
        public ContactItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.itemContent);
            mImageView = view.findViewById(R.id.itemImageView);
            mImageButton = view.findViewById(R.id.itemDeleteButton);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}