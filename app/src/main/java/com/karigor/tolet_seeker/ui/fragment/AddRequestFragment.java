package com.karigor.tolet_seeker.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.adapter.RequestAttributeAdapter;
import com.karigor.tolet_seeker.data.model.HouseModel;
import com.karigor.tolet_seeker.data.model.HouseBaseAttribute;
import com.karigor.tolet_seeker.data.model.HouseSwitchAttribute;
import com.karigor.tolet_seeker.data.model.HouseTextAttribute;
import com.karigor.tolet_seeker.listeners.RentSubmitListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class AddRequestFragment extends Fragment implements RequestAttributeAdapter.OnClickListener {

    private RecyclerView attributeRecyclerView;
    private Button submit_btn;
    private RequestAttributeAdapter requestAttributeAdapter;
    private RentSubmitListener rentSubmitListener;
    private View view;
    private ArrayList<HouseBaseAttribute> houseAttributeArrayList;

    private int switch_attribute_count = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Context context = getContext();

        if(context instanceof RentSubmitListener)
            rentSubmitListener = (RentSubmitListener) context;
        else
            rentSubmitListener = null;

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

                            HouseModel houseModel = convertHousePropertiesToParentModel();

                            if(rentSubmitListener !=null )
                                rentSubmitListener.addHouseRequest(houseModel);

                        }
                    }
                }
        );
    }

    private void submitButtonToggle(boolean enabled){

        if(enabled){
            submit_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
        else {
            submit_btn.setBackgroundColor(getResources().getColor(R.color.colorTextDisabled));

        }
    }

    private void setUpList() {

       // houseSwitchAttributeArrayList = new ArrayList<>();
        //houseTextAttributeArrayList = new ArrayList<>();
        houseAttributeArrayList = new ArrayList<>();

        HouseTextAttribute houseTextAttribute;

        String[] request_attributes = getResources().getStringArray(R.array.request_attributes);

        for(String attribute : request_attributes){

            if (getResources().getString(R.string.lift_status_label).equals(attribute)
                    || getResources().getString(R.string.gas_status_label).equals(attribute)
                    || getResources().getString(R.string.security_guard_status_label).equals(attribute)
                    || getResources().getString(R.string.parking_status_label).equals(attribute)) {

                HouseSwitchAttribute houseSwitchAttribute = new HouseSwitchAttribute(attribute, false);
               // houseSwitchAttributeArrayList.add(houseSwitchAttribute);
                houseAttributeArrayList.add(houseSwitchAttribute);
                switch_attribute_count++;
            }
            else if (getResources().getString(R.string.room_number_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 1, 10, "");
               // houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);
            }
            else if (getResources().getString(R.string.total_area_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 500, 5000, "");
               // houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);

            }
            else if (getResources().getString(R.string.floor_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 0, 50, "");
               // houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);

            }
            else if (getResources().getString(R.string.renting_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 500, 1000000, "");
                // houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);
            }
            else if ( getResources().getString(R.string.short_description_status_label).equals(attribute)) {

                houseTextAttribute = new HouseTextAttribute(attribute, 0, 0, "");
                //houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);
            }
            else if(getResources().getString(R.string.district_status_label).equals(attribute)){

                houseTextAttribute = new HouseTextAttribute(attribute,
                        getResources().getString(R.string.choose_option),
                        0,
                        0,
                        "");
               // houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);
            }
            else if(getResources().getString(R.string.title_label).equals(attribute)){

                houseTextAttribute =
                        new HouseTextAttribute(attribute, 0, 0, "");

                // houseTextAttributeArrayList.add(houseTextAttribute);
                houseAttributeArrayList.add(houseTextAttribute);
            }
        }

    }


    private HouseModel convertHousePropertiesToParentModel() {

        JSONObject parentmodelJson = new JSONObject();
        Gson gson = new Gson();

        for(HouseBaseAttribute childModel : houseAttributeArrayList)
        {
            try {
                String keyLabel = childModel.getLabel().replaceAll("[^a-zA-Z]+","_").toLowerCase();
                Log.e("keyLabel",keyLabel);
                if(childModel instanceof HouseTextAttribute) {
                    parentmodelJson.put(keyLabel, ((HouseTextAttribute)childModel).getValue());
                }
                else
                    parentmodelJson.put(keyLabel, ((HouseSwitchAttribute)childModel).isValue());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("json", "Json Parent Model = "+parentmodelJson.toString());
        return  gson.fromJson(parentmodelJson.toString(), HouseModel.class);

    }


    private void setUpRequestAttributeList() {

        requestAttributeAdapter = new RequestAttributeAdapter(
                getContext(),
                this,
                houseAttributeArrayList,
                switch_attribute_count);

        attributeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        attributeRecyclerView.setAdapter(requestAttributeAdapter);
    }


    private void handleAttributeListClick(final int recycler_adapter_position) {

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

                        ((HouseTextAttribute) houseAttributeArrayList
                                .get(recycler_adapter_position))
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

        boolean isValid = true;
        String msg = "Error";

        for(HouseBaseAttribute houseBaseAttribute : houseAttributeArrayList){

            if(houseBaseAttribute instanceof HouseTextAttribute){

                HouseTextAttribute houseTextAttribute = (HouseTextAttribute) houseBaseAttribute;

                if(houseTextAttribute.getValue().isEmpty() ||
                        (houseTextAttribute.getLabel().equals(getString(R.string.district_status_label))
                                && houseTextAttribute.getValue().equals("Choose Option"))
                )
                {
                    isValid = false;
                    msg = houseTextAttribute.getLabel();
                    break;
                }
            }
        }

        if(should_show_toast && !isValid)
            Toast.makeText(getContext(), "Fill up: "+msg, Toast.LENGTH_SHORT).show();

        submitButtonToggle(isValid);

        return isValid;
    }

    @Override
    public void onItemClick(RequestAttributeAdapter.ListViewHolder view, int position) {

        handleAttributeListClick( position);

    }

    @Override
    public void checkFormValidity(String label) {


        isFormValid(false);
    }


}


