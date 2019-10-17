package com.karigor.bloodseeker.data.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by touhid on 2019-10-17.
 * Email: udoy.touhid@gmail.com
 */
public class BloodRequestAttribute {

    //private int position;
    private String name;
    private ArrayList<String> options;

    public BloodRequestAttribute(){

        options = new ArrayList<>();
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void setOptions(String[] list) {
        options.addAll(Arrays.asList(list)) ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getPosition() {
//        return position;
//    }
//
//    public void setPosition(int position) {
//        this.position = position;
//    }
}
