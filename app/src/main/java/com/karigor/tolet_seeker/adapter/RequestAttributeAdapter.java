package com.karigor.tolet_seeker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.data.model.HouseSwitchAttribute;
import com.karigor.tolet_seeker.data.model.HouseTextAttribute;
import com.karigor.tolet_seeker.ui.fragment.AddRequestFragment;

import java.util.List;

/**
 * Created by touhid on 10/Nov/2019.
 * Email: udoy.touhid@gmail.com
 */

public class RequestAttributeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    Context context;

    private List<HouseSwitchAttribute> houseSwitchAttributeList;
    private List<HouseTextAttribute> houseListAttributeText;
    public OnClickListener onClickListener;
    private int textAttributeListOffset;

    public RequestAttributeAdapter(Context context,
                                   AddRequestFragment addRequestFragment,
                                   List<HouseSwitchAttribute> houseSwitchAttributeList,
                                   List<HouseTextAttribute> houseListAttributeText)
    {
        this.houseSwitchAttributeList = houseSwitchAttributeList;
        this.houseListAttributeText = houseListAttributeText;
        mInflater = LayoutInflater.from(context);
        onClickListener = null;
        this.context = context;

        textAttributeListOffset = houseSwitchAttributeList.size();

        onClickListener = (OnClickListener) addRequestFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case 0:
                itemView = mInflater.inflate(R.layout.item_switch_view, parent, false);
                viewHolder = new SwitchViewHolder(itemView);
                break;

            default:
                itemView = mInflater.inflate(R.layout.item_spinner, parent, false);
                viewHolder = new ListViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof SwitchViewHolder){

            ((SwitchViewHolder) holder).label.setText(houseSwitchAttributeList.get(position).getLabel());
           // Log.e("pos: "+position,houseSwitchAttributeList.get(position).getLabel());

        }
        else if(holder instanceof ListViewHolder){

            final int offsetPosition = position - textAttributeListOffset;
            final ListViewHolder listViewHolder = (ListViewHolder) holder;
            HouseTextAttribute houseTextAttribute = houseListAttributeText.get(offsetPosition);

            //Log.e("pos: "+position,offsetPosition+"...."+houseTextAttribute.getLabel());
            listViewHolder.label.setText(houseTextAttribute.getLabel());
            listViewHolder.label.setHint(houseTextAttribute.getSubTitle());


            if(houseTextAttribute.getLabel().equals(context.getResources().getString(R.string.district_status_label))) {

                listViewHolder.editText.setVisibility(View.GONE);
                listViewHolder.linearLayout.setVisibility(View.VISIBLE);
                listViewHolder.textView.setText(houseTextAttribute.getValue());
                listViewHolder.textView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                               // Log.e("on click","on click");
                                onClickListener.onItemClick(listViewHolder, offsetPosition);
                            }
                        }
                );

            }
            else{
                //listViewHolder.editText.setOnClickListener(null);

                listViewHolder.editText.setVisibility(View.VISIBLE);
                listViewHolder.linearLayout.setVisibility(View.GONE);
                if(houseTextAttribute.getLabel().equals(context.getResources().getString(R.string.short_description_status_label))){


                }
//                else
//                    listViewHolder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                //listViewHolder.editText.setEnabled(true);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position < houseSwitchAttributeList.size())
            return 0;

        return 1;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return houseSwitchAttributeList.size()+ houseListAttributeText.size();
    }

    private class SwitchViewHolder extends RecyclerView.ViewHolder {
        private final TextView label;
        private final SwitchCompat switchCompat;

        private SwitchViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            switchCompat = itemView.findViewById(R.id.switchWidget);
        }
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

    }
}