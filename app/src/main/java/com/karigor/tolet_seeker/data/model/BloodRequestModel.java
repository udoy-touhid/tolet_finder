package com.karigor.tolet_seeker.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

/**
 * Restaurant POJO.
 */
@IgnoreExtraProperties
public class BloodRequestModel {




    public static final String COLLECTION_BLOOD_REQUESTS = "blood_requests";
    public static final String FIELD_DISTRICT = "district";
    public static final String FIELD_PATIENT_TYPE = "patientType";
    public static final String FIELD_BLOOD_GROUP = "blood_group";
    public static final String FIELD_PATIENT_CONDITION = "patient_condition";

    private String user_id;
    private String user_name;
    private String user_number;
    private String user_email;
    private String profilePictureUrl;
    private String district;
    private String patientType;
    private String blood_group;
    private String description;
    private String hospital_name;
    private double hospital_latitude;
    private double hospital_longitue;
    private long donation_time_stamp;
    private List<String> photoUrlList;
    private int bags_needed;
    private String patient_condition;
    private boolean postActiveFlag;

    public BloodRequestModel() {}



    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public double getHospital_latitude() {
        return hospital_latitude;
    }

    public void setHospital_latitude(double hospital_latitude) {
        this.hospital_latitude = hospital_latitude;
    }

    public double getHospital_longitue() {
        return hospital_longitue;
    }

    public void setHospital_longitue(double hospital_longitue) {
        this.hospital_longitue = hospital_longitue;
    }

    public long getDonation_time_stamp() {
        return donation_time_stamp;
    }

    public void setDonation_time_stamp(long donation_time_stamp) {
        this.donation_time_stamp = donation_time_stamp;
    }

    public List<String> getPhotoUrlList() {
        return photoUrlList;
    }

    public void setPhotoUrlList(List<String> photoUrlList) {
        this.photoUrlList = photoUrlList;
    }

    public int getBags_needed() {
        return bags_needed;
    }

    public void setBags_needed(int bags_needed) {
        this.bags_needed = bags_needed;
    }


    public boolean isPostActiveFlag() {
        return postActiveFlag;
    }

    public void setPostActiveFlag(boolean postActiveFlag) {
        this.postActiveFlag = postActiveFlag;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPatient_condition() {
        return patient_condition;
    }

    public void setPatient_condition(String patient_condition) {
        this.patient_condition = patient_condition;
    }
}
