package com.karigor.bloodseeker.ui.fragment;


import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.karigor.bloodseeker.R;
import com.karigor.bloodseeker.Utility;
import com.karigor.bloodseeker.adapter.BloodRequestAttributeAdapter;
import com.karigor.bloodseeker.data.model.BloodRequestAttributeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CreateBloodRequestFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView bloodAttributeListView;
    private TextView attributeLabel;
    private EditText hopitalName;
    Button submit_btn;
    BloodRequestAttributeAdapter bloodRequestAttributeAdapter;

    boolean all_input_valid;

    private View view;
    ArrayList<BloodRequestAttributeModel> bloodRequestAttributeModelArrayList;

    public static CreateBloodRequestFragment newInstance(String param1, String param2) {
        CreateBloodRequestFragment fragment = new CreateBloodRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        all_input_valid = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_blood_request, container, false);
        bloodAttributeListView = view.findViewById(R.id.attributes_list_view);

        attributeLabel = view.findViewById(R.id.attribute_name);
        attributeLabel.setText(getResources().getString(R.string.hospital_name));

        hopitalName = view.findViewById(R.id.attribute_value);

        setUpList();
        setUpRequestAttributeList();
        setUpSubmit();
        setHospitalNameWatcher();
        return view;
    }

    private void setHospitalNameWatcher() {

        hopitalName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                isFormValid(false);
            }
        });
    }

    private void setUpSubmit() {

        submit_btn = view.findViewById(R.id.submit_request);
        submitButtonToggle(false);
        submit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(isFormValid(true)){

                            Toast.makeText(
                                    getContext(),
                                    "Opening Newsfeed",
                                    Toast.LENGTH_SHORT)
                                    .show();

                            //save to cloud and precess to newsfeed
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

        bloodRequestAttributeModelArrayList = new ArrayList<>();

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.blood_attribute_list);
        int n = ta.length();
        String[][] array = new String[n][];

        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {

                array[i] = res.getStringArray(id);
                BloodRequestAttributeModel bloodRequestAttributeModel = new BloodRequestAttributeModel();
                bloodRequestAttributeModel.setName(array[i][0]);
                bloodRequestAttributeModel.setOptions(array[i]);
                bloodRequestAttributeModel.setSelected_option(bloodRequestAttributeModel.getOptions().get(0));
                bloodRequestAttributeModelArrayList.add(bloodRequestAttributeModel);
                //Log.w("tag",array[i][0]);


            } else {
                // something wrong with the XML
            }
        }
        ta.recycle(); // Important!
    }

    private void setUpRequestAttributeList() {

        //Log.w("size",bloodRequestAttributeModelArrayList.size()+"");
        bloodRequestAttributeAdapter = new BloodRequestAttributeAdapter(getContext(), bloodRequestAttributeModelArrayList);
        bloodAttributeListView.setAdapter(bloodRequestAttributeAdapter);

        bloodAttributeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleAttributeListClick(view, position);
            }
        });

    }

    private void handleAttributeListClick(final View view, final int blood_atttribute_listPosition) {

        final TextView attribute_value = (TextView) view.findViewById(R.id.attribute_value);

        final BloodRequestAttributeModel productsAttribute = bloodRequestAttributeModelArrayList.get(blood_atttribute_listPosition);



        ArrayList<String> attributeValues = productsAttribute.getOptions();

        final ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        optionsAdapter.clear();
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
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String selectedItem = optionsAdapter.getItem(position);
                        attribute_value.setText(selectedItem);
                        //optionsAdapter.ge
                        bloodRequestAttributeModelArrayList.get(blood_atttribute_listPosition).setSelected_option(selectedItem);
                        isFormValid(false);

                        alertDialog.dismiss();

                    }
                }
        );

    }





    private boolean isFormValid(boolean should_show_toast){


        Collections.sort(bloodRequestAttributeModelArrayList, new Comparator<BloodRequestAttributeModel>() {

            public int compare(BloodRequestAttributeModel o1, BloodRequestAttributeModel o2) {
                // compare two instance of `Score` and return `int` as result.
                return o2.getName().compareTo(o1.getName());
            }
        });

        boolean flag = true;
        String msg = "Error";
        for(BloodRequestAttributeModel bloodRequestAttributeModel : bloodRequestAttributeModelArrayList){

            Log.w("isFormValid",bloodRequestAttributeModel.getName());
            if(bloodRequestAttributeModel.getSelected_option().equals(getResources().getString(R.string.choose_option)))
            {
                msg = "Fill Up "+bloodRequestAttributeModel.getName();

                flag = false;
                break;
            }
        }

        if(flag && hopitalName.getText().toString().isEmpty()) {

            flag = false;
            msg = "Fill Up "+getResources().getString(R.string.hospital_name);
        }

        if(should_show_toast && !flag){
            Utility.Toaster(getContext(),msg);
        }

        submitButtonToggle(flag);
        return flag;

    }

}

