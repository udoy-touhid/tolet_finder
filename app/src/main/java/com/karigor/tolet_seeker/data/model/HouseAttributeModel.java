package com.karigor.tolet_seeker.data.model;

import java.util.List;

/**
 * Created by touhid on 2019-10-17.
 * Email: udoy.touhid@gmail.com
 */
public class HouseAttributeModel {

    //private int position;
    private int room_number;
    private boolean lift_available;
    private boolean gas_available;
    private int total_area_sq;
    private int floor_no;
    private boolean security_guard_available;
    private boolean parking_guard_available;
    private int rent;
    private boolean rent_negotialble;
    private double latitude;
    private double longitude;
    private String description;
    private String rent_status;
    private String house_no;
    private String road_no;
    private List<PhotoModel> photoModelList;

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public boolean isLift_available() {
        return lift_available;
    }

    public void setLift_available(boolean lift_available) {
        this.lift_available = lift_available;
    }

    public boolean isGas_available() {
        return gas_available;
    }

    public void setGas_available(boolean gas_available) {
        this.gas_available = gas_available;
    }

    public int getTotal_area_sq() {
        return total_area_sq;
    }

    public void setTotal_area_sq(int total_area_sq) {
        this.total_area_sq = total_area_sq;
    }

    public int getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(int floor_no) {
        this.floor_no = floor_no;
    }

    public boolean isSecurity_guard_available() {
        return security_guard_available;
    }

    public void setSecurity_guard_available(boolean security_guard_available) {
        this.security_guard_available = security_guard_available;
    }

    public boolean isParking_guard_available() {
        return parking_guard_available;
    }

    public void setParking_guard_available(boolean parking_guard_available) {
        this.parking_guard_available = parking_guard_available;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    private String area_no;


    public List<PhotoModel> getPhotoModelList() {
        return photoModelList;
    }

    public void setPhotoModelList(List<PhotoModel> photoModelList) {
        this.photoModelList = photoModelList;
    }
}
