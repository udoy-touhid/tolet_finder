package com.karigor.tolet_seeker.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.data.model.HouseBaseAttribute;
import com.karigor.tolet_seeker.data.model.HouseSwitchAttribute;
import com.karigor.tolet_seeker.data.model.HouseTextAttribute;
import com.karigor.tolet_seeker.ui.fragment.AddRequestFragment;

import java.util.ArrayList;

/**
 * Created by touhid on 10/Nov/2019.
 * Email: udoy.touhid@gmail.com
 */

public class RequestAttributeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private Context context;
    private ArrayList<HouseBaseAttribute> houseAttributeArrayList;

    private OnClickListener onClickListener;
    private final int textAttributeListOffset;

    private final int SWITCH_VIEW_HOLDER_TYPE = 0, TEXT_VIEWHOLDER_TYPE = 1;


    public RequestAttributeAdapter(Context context,
                                   AddRequestFragment addRequestFragment,
                                   ArrayList<HouseBaseAttribute> houseAttributeArrayList,
                                   int textAttributeListOffset)
    {

        this.houseAttributeArrayList = houseAttributeArrayList;
        mInflater = LayoutInflater.from(context);
        onClickListener = null;
        this.context = context;

        this.textAttributeListOffset = textAttributeListOffset;

        onClickListener = (OnClickListener) addRequestFragment;

        //Log.w("textAttributeListOffset","off:"+textAttributeListOffset);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case SWITCH_VIEW_HOLDER_TYPE:
                itemView = mInflater.inflate(R.layout.item_switch_view, parent, false);
                viewHolder = new SwitchViewHolder(itemView);
                break;

            case TEXT_VIEWHOLDER_TYPE:
                itemView = mInflater.inflate(R.layout.item_spinner, parent, false);
                viewHolder = new ListViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(position < textAttributeListOffset){

            final SwitchViewHolder switchViewHolder = (SwitchViewHolder) holder;
            try {

                final HouseSwitchAttribute houseSwitchAttribute = (HouseSwitchAttribute) houseAttributeArrayList.get(position);

                //Log.w("houseSwitchAttribute", houseSwitchAttribute.getLabel() + "...." + houseSwitchAttribute.isValue());

                switchViewHolder.label.setText(houseAttributeArrayList.get(position).getLabel());
                switchViewHolder.switchCompat.setChecked(houseSwitchAttribute.isValue());
                switchViewHolder.switchLabel.setText(
                        houseSwitchAttribute.isValue() ? R.string.switch_label_yes : R.string.switch_label_no
                );

                switchViewHolder.switchCompat.setOnCheckedChangeListener(
                        new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                houseSwitchAttribute.setValue(isChecked);
                                switchViewHolder.switchLabel.setText(
                                        isChecked ? R.string.switch_label_yes : R.string.switch_label_no
                                );

                            }
                        }
                );
            }catch (ClassCastException e){

                Log.e("ClassCast: switch: "+position,houseAttributeArrayList.get(position).getLabel());
            }

        }
        else// if(holder instanceof ListViewHolder)
        {

            final ListViewHolder listViewHolder = (ListViewHolder) holder;
            try {
                final HouseTextAttribute houseTextAttribute = (HouseTextAttribute) houseAttributeArrayList.get(position);

//                Log.w("HouseTextAttribute", houseTextAttribute.getLabel() + "...." + houseTextAttribute.getValue());

                listViewHolder.label.setText(houseTextAttribute.getLabel());
                listViewHolder.label.setHint(houseTextAttribute.getSubTitle());
                listViewHolder.editText.setText(houseTextAttribute.getValue());
                listViewHolder.textView.setText(houseTextAttribute.getValue());


                //select value form dialog
                if (houseTextAttribute.getLabel().equals(context.getResources().getString(R.string.district_status_label))) {

                    listViewHolder.editText.setVisibility(View.GONE);
                    listViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    listViewHolder.textView.setText(houseTextAttribute.getValue());


                }
                //edit text visible
                else {

                    listViewHolder.textView.setOnClickListener(null);
                    listViewHolder.editText.setVisibility(View.VISIBLE);
                    listViewHolder.linearLayout.setVisibility(View.GONE);

                    listViewHolder.editText.setText(houseTextAttribute.getValue());

                    if (houseTextAttribute.getLabel().equals(context.getResources().getString(R.string.short_description_status_label))) {
                        //any text
                        listViewHolder.editText.setInputType(InputType.TYPE_CLASS_TEXT);



                    } else {
                        //only number
                        listViewHolder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);


                    }
                }
            }catch (ClassCastException e){

                Log.e("ClassCast: text: "+position,houseAttributeArrayList.get(position).getLabel()+" "+(houseAttributeArrayList.get(position) instanceof HouseSwitchAttribute)+" "+(houseAttributeArrayList.get(position) instanceof HouseTextAttribute));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(position < textAttributeListOffset)
            return SWITCH_VIEW_HOLDER_TYPE;

        return TEXT_VIEWHOLDER_TYPE;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        return houseAttributeArrayList.size();
    }

    private class SwitchViewHolder extends RecyclerView.ViewHolder {
        private final TextView label,switchLabel;
        private final SwitchCompat switchCompat;

        private SwitchViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.label);
            switchCompat = itemView.findViewById(R.id.switchWidget);
            switchLabel = itemView.findViewById(R.id.switch_label);
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

            textView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Log.w("checkFormValidity", "textView");

                            // Log.e("on click","on click");
                            onClickListener.onItemClick(ListViewHolder.this, getAdapterPosition());
                        }
                    }
            );

            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                   // Log.w("Adapter FormValidity", "edittext");
                    if (!((HouseTextAttribute)houseAttributeArrayList.get(getAdapterPosition())).getValue().equals(s.toString())) {
                        ((HouseTextAttribute)houseAttributeArrayList.get(getAdapterPosition())).setValue(s.toString());

                        onClickListener.checkFormValidity(((HouseTextAttribute)houseAttributeArrayList.get(getAdapterPosition())).getLabel());
                    }


                }
            });
        }
    }

    public interface OnClickListener{

        public void onItemClick(ListViewHolder view, int position);
        public void checkFormValidity(String label);

    }
}