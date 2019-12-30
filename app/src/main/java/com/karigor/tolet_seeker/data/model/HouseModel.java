package com.karigor.tolet_seeker.data.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by touhid on 2019-10-17.
 * Email: udoy.touhid@gmail.com
 */

@IgnoreExtraProperties
public class HouseModel implements Serializable {

    public static final String COLLECTION_RENT_REQUESTS = "rent_requests";

    private String user_id;
    private String user_name;

    private String title;
    private int number_of_rooms;
    private boolean lift_available;
    private boolean gas_line_available;
    private int total_area;
    private int floor_number;
    private boolean security_guard_available;
    private boolean parking_available;
    private int house_rent;
    private String district;
    private String short_description;

    private boolean rent_negotialble;
    private double latitude;
    private double longitude;
    private String rent_status;
    private String house_no;
    private String road_no;
    private String area_no;
    private List<PhotoModel> photoModelList;

    public int getNumber_of_rooms() {
        return number_of_rooms;
    }

    public void setNumber_of_rooms(int number_of_rooms) {
        this.number_of_rooms = number_of_rooms;
    }

    public boolean isLift_available() {
        return lift_available;
    }

    public void setLift_available(boolean lift_available) {
        this.lift_available = lift_available;
    }

    public boolean isGas_line_available() {
        return gas_line_available;
    }

    public void setGas_line_available(boolean gas_line_available) {
        this.gas_line_available = gas_line_available;
    }

    public int getTotal_area() {
        return total_area;
    }

    public void setTotal_area(int total_area) {
        this.total_area = total_area;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }

    public boolean isSecurity_guard_available() {
        return security_guard_available;
    }

    public void setSecurity_guard_available(boolean security_guard_available) {
        this.security_guard_available = security_guard_available;
    }

    public boolean isParking_available() {
        return parking_available;
    }

    public void setParking_available(boolean parking_available) {
        this.parking_available = parking_available;
    }

    public int getHouse_rent() {
        return house_rent;
    }

    public void setHouse_rent(int house_rent) {
        this.house_rent = house_rent;
    }

    public boolean isRent_negotialble() {
        return rent_negotialble;
    }

    public void setRent_negotialble(boolean rent_negotialble) {
        this.rent_negotialble = rent_negotialble;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRent_status() {
        return rent_status;
    }

    public void setRent_status(String rent_status) {
        this.rent_status = rent_status;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getRoad_no() {
        return road_no;
    }

    public void setRoad_no(String road_no) {
        this.road_no = road_no;
    }

    public String getArea_no() {
        return area_no;
    }

    public void setArea_no(String area_no) {
        this.area_no = area_no;
    }



    public List<PhotoModel> getPhotoModelList() {
        return photoModelList;
    }

    public void setPhotoModelList(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String  getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
