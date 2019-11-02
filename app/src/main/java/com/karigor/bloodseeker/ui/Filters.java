package com.karigor.bloodseeker.ui;

import android.content.Context;
import android.text.TextUtils;

import com.google.firebase.firestore.Query;

/**
 * Object for passing filters around.
 */
public class Filters {

    private String patientType = null;
    private String district = null;
    private String blood_group = null;



    private String patient_condition = null;

    private String sortBy = null;

    private Query.Direction sortDirection = null;

    public Filters() {}

    public static Filters getDefault() {
        Filters filters = new Filters();
//        filters.setSortBy(Restaurant.FIELD_AVG_RATING);
//        filters.setSortDirection(Query.Direction.DESCENDING);

        return filters;
    }

    public boolean hasPatientType() {
        return !(TextUtils.isEmpty(patientType));
    }

    public boolean hasDistrict() {
        return !(TextUtils.isEmpty(district));
    }

    public boolean hasBloodGroup() {
        return !(TextUtils.isEmpty(blood_group));
    }
    public boolean hasPatientCondition() {
        return !(TextUtils.isEmpty(patient_condition));
    }

    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }


    public String getSearchDescription(Context context) {
        StringBuilder desc = new StringBuilder();

        if(patientType != null || blood_group != null || patient_condition != null) {

            if(blood_group != null)
                desc.append("<b>").append(blood_group).append("</b>").append(" Blood");

            if(patient_condition != null) {
                if (!desc.toString().isEmpty())
                    desc.append(",");

                desc.append(" <b>").append(patient_condition).append("</b>");
            }

            if(patientType != null) {

                desc.append(" <b>").append(patientType).append("</b>");
            }

            if(patientType != null || patient_condition != null)
                desc.append(" Patient");

            if(district != null)
                desc.append(" in ").append("<b>").append(district).append("</b>");
        }
        else if(district != null)
            desc.append("<b>").append(district).append("</b>");


        if(desc.toString().isEmpty())
            return "All Requests";

        return desc.toString();
    }

    public String getOrderDescription(Context context) {
//        if (Restaurant.FIELD_PRICE.equals(sortBy)) {
//            return context.getString(R.string.sorted_by_price);
//        } else if (Restaurant.FIELD_POPULARITY.equals(sortBy)) {
//            return context.getString(R.string.sorted_by_popularity);
//        } else {
//            return context.getString(R.string.sorted_by_rating);
//        }
        return "sorted by";
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Query.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Query.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getPatient_condition() {
        return patient_condition;
    }

    public void setPatient_condition(String patient_condition) {
        this.patient_condition = patient_condition;
    }
}
