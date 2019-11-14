package com.karigor.tolet_seeker.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.adapter.RequestAttributeAdapter;
import com.karigor.tolet_seeker.data.model.HouseAttributeModel;
import com.karigor.tolet_seeker.data.model.HouseSwitchAttribute;
import com.karigor.tolet_seeker.data.model.HouseTextAttribute;
import com.karigor.tolet_seeker.listeners.RentSubmitListener;

import java.util.ArrayList;
import java.util.Arrays;


public class AddRequestFragment extends Fragment implements RequestAttributeAdapter.OnClickListener {

    private RecyclerView attributeRecyclerView;
   //private TextView attributeLabel;
    //private EditText hopitalName;
    private Button submit_btn;
    private RequestAttributeAdapter requestAttributeAdapter;

    private RentSubmitListener rentSubmitListener;

    private boolean all_input_valid;

    private View view;
    private ArrayList<HouseSwitchAttribute> houseSwitchAttributeArrayList;
    private ArrayList<HouseTextAttribute> houseTextAttributeArrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Context context = getContext();

        if(context instanceof RentSubmitListener)
            rentSubmitListener = (RentSubmitListener) context;
        else
            rentSubmitListener = null;

        all_input_valid = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_request, container, false);

        attributeRecyclerView = view.findViewById(R.id.recycler_view);
        setUpList();
        setUpRequestAttributeList();
        setUpSubmit();

        return view;
    }



    private void setUpSubmit() {

        submit_btn = view.findViewById(R.id.submit_request);
        submitButtonToggle(false);
        submit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(isFormValid(true)){

                            HouseAttributeModel houseAttributeModel = new HouseAttributeModel();

                            //save to cloud and precess to newsfeed
                            if(rentSubmitListener != null)
                                rentSubmitListener.addHouseRequest(houseAttributeModel);
                        }
                    }
                }
        );
    }

    private void submitButtonToggle(boolean enabled){

        all_input_valid = enabled;

        if(enabled){

            submit_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }
        else {
            submit_btn.setBackgroundColor(getResources().getColor(R.color.colorTextDisabled));

        }
    }

    private void setUpList() {

        houseSwitchAttributeArrayList = new ArrayList<>();
        houseTextAttributeArrayList = new ArrayList<>();

        HouseTextAttribute houseTextAttribute;

        String[] request_attributes = getResources().getStringArray(R.array.request_attributes);

        for(String attribute : request_attributes){

            if (getResources().getString(R.string.lift_status_label).equals(attribute)
                    || getResources().getString(R.string.gas_status_label).equals(attribute)
                    || getResources().getString(R.string.security_guard_status_label).equals(attribute)
                    || getResources().getString(R.string.parking_status_label).equals(attribute)) {

                HouseSwitchAttribute houseSwitchAttribute = new HouseSwitchAttribute(attribute, false);
                houseSwitchAttributeArrayList.add(houseSwitchAttribute);
            }
            else if (getResources().getString(R.string.room_number_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 1, 10, "");
                houseTextAttributeArrayList.add(houseTextAttribute);
            }
            else if (getResources().getString(R.string.total_area_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 500, 5000, "");
                houseTextAttributeArrayList.add(houseTextAttribute);
            }
            else if (getResources().getString(R.string.floor_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 0, 50, "");
                houseTextAttributeArrayList.add(houseTextAttribute);
            }
            else if ( getResources().getString(R.string.short_description_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 0, 0, "");
                houseTextAttributeArrayList.add(houseTextAttribute);
            }
            else if(getResources().getString(R.string.district_status_label).equals(attribute)){

                houseTextAttribute = new HouseTextAttribute(attribute,
                        getResources().getString(R.string.choose_option),
                        0,
                        0,
                        "");
                houseTextAttributeArrayList.add(houseTextAttribute);
            }
        }

    }

    private void setUpRequestAttributeList() {

        //Log.w("size",houseAttributeModelArrayList.size()+"");
        requestAttributeAdapter = new RequestAttributeAdapter(getContext(),
                this,
                houseSwitchAttributeArrayList,houseTextAttributeArrayList);

        attributeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        attributeRecyclerView.setAdapter(requestAttributeAdapter);

    }

    @Override
    public void onItemClick(RequestAttributeAdapter.ListViewHolder view, int position) {

        handleAttributeListClick( position);

    }

    private void handleAttributeListClick(final int position) {

        ArrayList<String> attributeValues = new ArrayList<String>(
                Arrays.asList(getResources().getStringArray(R.array.district_list))
        );

        final ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

        optionsAdapter.add(getResources().getString(R.string.choose_option));
        optionsAdapter.addAll(attributeValues);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_list, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
        //TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);

        //dialog_title.setText(selectedAttributeName);
        dialog_list.setAdapter(optionsAdapter);

        final AlertDialog alertDialog = dialog.create();

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.dimAmount = 0.2f;

        alertDialog.show();


        dialog_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,final int pos, long id) {

                        houseTextAttributeArrayList
                                .get(position)
                                .setValue(optionsAdapter.getItem(pos));
                        requestAttributeAdapter.notifyDataSetChanged();
                        //Log.e("houseTextAteArrayList",optionsAdapter.getItem(pos));
                        isFormValid(false);
                        alertDialog.dismiss();
                    }
                }
        );

    }




    private boolean isFormValid(boolean should_show_toast){


        boolean flag = true;
        String msg = "Error";
//        for(HouseAttributeModel houseAttributeModel : houseAttributeModelArrayList){
//
//            Log.w("isFormValid", houseAttributeModel.getName());
//            if(houseAttributeModel.getSelected_option().equals(getResources().getString(R.string.choose_option)))
//            {
//                msg = "Fill Up "+ houseAttributeModel.getName();
//
//                flag = false;
//                break;
//            }
//        }
//
//        if(flag && hopitalName.getText().toString().isEmpty()) {
//
//            flag = false;
//            msg = "Fill Up "+getResources().getString(R.string.hospital_name);
//        }
//
//        if(should_show_toast && !flag){
//            Utility.Toaster(getContext(),msg);
//        }
//
//        submitButtonToggle(flag);
        return flag;
    }



}


