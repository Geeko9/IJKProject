package com.geeko.proto.ijkproject.app.data;

import android.graphics.drawable.Drawable;

/**
 * @author Kim Seonyong
 * @version 1.1 April 23 2014
 * @since 1.1
 *
 */
public class Item {
    private Drawable icon;
    private String name;
    private String phone;
    private String location;
    private String machine;
    private String status;

    public Drawable getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getMachine() {
        return machine;
    }

    public String getStatus() {
        return status;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
