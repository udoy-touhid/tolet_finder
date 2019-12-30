package com.karigor.tolet_seeker.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karigor.tolet_seeker.R;

import java.util.ArrayList;

/**
 * Created by touhid on 10/Nov/2019.
 * Email: udoy.touhid@gmail.com
 */

public class PostDetailsAttributeAdapter extends RecyclerView.Adapter<PostDetailsAttributeAdapter.ListViewHolder> {

    private final LayoutInflater mInflater;
    private Context context;
    private ArrayList<Pair<String,String>> houseAttributeArrayList;

    private OnClickListener onClickListener;



    public PostDetailsAttributeAdapter(Context context,
                                       ArrayList<Pair<String,String>> houseAttributeArrayList)
    {

        this.houseAttributeArrayList = houseAttributeArrayList;
        mInflater = LayoutInflater.from(context);
        onClickListener = null;
        this.context = context;

    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        ListViewHolder viewHolder = null;

        itemView = mInflater.inflate(R.layout.item_spinner, parent, false);
        viewHolder = new ListViewHolder(itemView);

        viewHolder.editText.setVisibility(View.GONE);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        holder.label.setText(houseAttributeArrayList.get(position).first);
        holder.textView.setText(houseAttributeArrayList.get(position).second);

    }

    @Override
    public int getItemCount() {
        return houseAttributeArrayList.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView label;
        private final EditText editText;
        private final TextView textView;
        private final LinearLayout linearLayout;

        private ListViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            editText = itemView.findViewById(R.id.editText);
            textView = itemView.findViewById(R.id.textView);
            linearLayout = itemView.findViewById(R.id.linear_layout);


        }
    }

    public interface OnClickListener{

        public void onItemClick(ListViewHolder view, int position);
        public void checkFormValidity(String label);

    }
}