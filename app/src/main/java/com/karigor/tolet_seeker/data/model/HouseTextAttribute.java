package com.karigor.tolet_seeker.data.model;

/**
 * Created by touhid on 11/Nov/2019.
 * Email: udoy.touhid@gmail.com
 */
public class HouseTextAttribute extends HouseBaseAttribute {

    private String value;
    int startRange;
    int endRange;
    String subTitle;

    public HouseTextAttribute(String label, String value, int startRange, int endRange, String subTitle) {
        this.label = label;
        this.value = value;
        this.startRange = startRange;
        this.endRange = endRange;
        this.subTitle = subTitle;
    }

    public HouseTextAttribute(String label, int startRange, int endRange, String subTitle) {
        this.label = label;
        this.value = "";
        this.startRange = startRange;
        this.endRange = endRange;
        this.subTitle = subTitle;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public void setEndRange(int endRange) {
        this.endRange = endRange;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
