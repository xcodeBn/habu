package com.example.habuonlineshop.Classes;

public class Holder {

    private Integer icon;
    private String text;

    public Holder(Integer icon, String text) {

        this.icon = icon;
        this.text = text;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
