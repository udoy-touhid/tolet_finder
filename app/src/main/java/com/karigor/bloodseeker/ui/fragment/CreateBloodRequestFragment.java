package com.karigor.bloodseeker.ui.fragment;


import android.content.res.Resources;
import android.content.res.TypedArray;
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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.karigor.bloodseeker.R;
import com.karigor.bloodseeker.adapter.BloodRequestAttributeAdapter;
import com.karigor.bloodseeker.data.model.BloodRequestAttribute;

import java.util.ArrayList;

public class CreateBloodRequestFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView bloodAttributeListView;

    private View view;
    ArrayList<BloodRequestAttribute> bloodRequestAttributeArrayList;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_blood_request, container, false);
        bloodAttributeListView = view.findViewById(R.id.attributes_list_view);
        setUpList();
        setUpRequestAttributeList();
        return view;
    }

    private void setUpList() {

        bloodRequestAttributeArrayList = new ArrayList<>();

        Resources res = getResources();
        TypedArray ta = res.obtainTypedArray(R.array.blood_attribute_list);
        int n = ta.length();
        String[][] array = new String[n][];

        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {

                array[i] = res.getStringArray(id);
                BloodRequestAttribute bloodRequestAttribute = new BloodRequestAttribute();
                bloodRequestAttribute.setName(array[i][0]);
                bloodRequestAttribute.setOptions(array[i]);
                bloodRequestAttributeArrayList.add(bloodRequestAttribute);
                //Log.w("tag",array[i][0]);


            } else {
                // something wrong with the XML
            }
        }
        ta.recycle(); // Important!
    }

    private void setUpRequestAttributeList() {

        Log.w("size",bloodRequestAttributeArrayList.size()+"");
        BloodRequestAttributeAdapter bloodRequestAttributeAdapter = new BloodRequestAttributeAdapter(getContext(), bloodRequestAttributeArrayList);
        bloodAttributeListView.setAdapter(bloodRequestAttributeAdapter);

        bloodAttributeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleAttributeListClick(view, position);
            }
        });

    }

    private void handleAttributeListClick(final View view, final int position) {

        final TextView attribute_value = (TextView) view.findViewById(R.id.attribute_value);

        final BloodRequestAttribute productsAttribute = bloodRequestAttributeArrayList.get(position);



        ArrayList<String> attributeValues = productsAttribute.getOptions();
//        attributeValues.add(getContext().getString(R.string.choose_option));
//
//        ///todo get option of all values for a single variation...size: large, medium
//        attributeValues.addAll(productsAttribute.getOptions());
//
//        //todo attributesList has all the types attribute list
//        //todo ...means a list of product attribute, which has a list of options!
//

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

        //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.dimAmount = 0.2f;
        //alertDialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        //int[] screen_locations = new int[2];
        //view.getLocationOnScreen(screen_locations);
        //wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        //wmlp.x = screen_locations[0];   //x position
        //wmlp.y = screen_locations[1];   //y position

        alertDialog.show();

    }

}

