package com.karigor.tolet_seeker.data.model;

/**
 * Created by touhid on 11/Nov/2019.
 * Email: udoy.touhid@gmail.com
 */

public class HouseSwitchAttribute extends HouseBaseAttribute {

    boolean value;

    public HouseSwitchAttribute(String label, boolean value) {
        this.label = label;
        this.value = value;
    }


    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
