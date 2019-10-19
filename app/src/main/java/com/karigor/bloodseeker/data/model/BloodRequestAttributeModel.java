package com.karigor.bloodseeker.data.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by touhid on 2019-10-17.
 * Email: udoy.touhid@gmail.com
 */
public class BloodRequestAttributeModel {

    //private int position;
    private String name;
    private ArrayList<String> options;
    private String selected_option;

    public BloodRequestAttributeModel(){

        options = new ArrayList<>();
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void setOptions(String[] list) {

        options.add("Choose Option");

        String [] newArray = Arrays.copyOfRange(list, 1, list.length);
        options.addAll(Arrays.asList(newArray)) ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelected_option() {
        return selected_option;
    }

    public void setSelected_option(String selected_option) {
        this.selected_option = selected_option;
    }

//    public int getPosition() {
//        return position;
//    }
//
//    public void setPosition(int position) {
//        this.position = position;
//    }
}
