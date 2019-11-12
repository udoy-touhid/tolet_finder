package com.karigor.tolet_seeker.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.karigor.tolet_seeker.R;


/**
 * Dialog Fragment containing filter form.
 */
public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "FilterDialog";

    public interface FilterListener {

        void onFilter(Filters filters);

    }

    private View mRootView;

    private Spinner mPatientTypeSpinner;
    private Spinner mDistrictSpinner;
    private Spinner mPatientConditionSpinner;
    private Spinner mBloodGroupSpinner;

    private FilterListener mFilterListener;


    public FilterDialogFragment(){ }

    public FilterDialogFragment(FilterListener filterListener){

        this.mFilterListener = filterListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_filters, container, false);

        mPatientTypeSpinner = mRootView.findViewById(R.id.spinnerPatientType);
        mDistrictSpinner = mRootView.findViewById(R.id.spinnerDistrict);
        mPatientConditionSpinner = mRootView.findViewById(R.id.spinnerPatientCondition);
        mBloodGroupSpinner = mRootView.findViewById(R.id.spinnerBloodGroup);

        mRootView.findViewById(R.id.buttonSearch).setOnClickListener(this);
        mRootView.findViewById(R.id.buttonCancel).setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
        else Log.e(FilterDialogFragment.TAG,"onAttach context is not an instance of fliter");
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }

        dismiss();
    }

    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedPatientType() {
        String selected = (String) mPatientTypeSpinner.getSelectedItem();
        if (getString(R.string.value_any_patient_type).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedDistrict() {
        String selected = (String) mDistrictSpinner.getSelectedItem();
        if (getString(R.string.value_any_district).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedBloodGroup() {
        String selected = (String) mBloodGroupSpinner.getSelectedItem();
        if (getString(R.string.value_any_blood_group).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedPatientCondition() {
        String selected = (String) mPatientConditionSpinner.getSelectedItem();
        if (getString(R.string.value_any_patient_condition).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }



    public void resetFilters() {
        if (mRootView != null) {
            mPatientTypeSpinner.setSelection(0);
            mDistrictSpinner.setSelection(0);
            mBloodGroupSpinner.setSelection(0);
            mPatientConditionSpinner.setSelection(0);
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {
            filters.setBlood_group(getSelectedBloodGroup());
            filters.setDistrict(getSelectedDistrict());
            filters.setPatientType(getSelectedPatientType());
            filters.setPatient_condition(getSelectedPatientCondition());
//            filters.setSortBy(getSelectedSortBy());
//            filters.setSortDirection(getSortDirection());
        }

        return filters;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSearch:
                onSearchClicked();
                break;
            case R.id.buttonCancel:
                onCancelClicked();
                break;
        }
    }
}
