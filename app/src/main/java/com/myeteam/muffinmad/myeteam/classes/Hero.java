package com.myeteam.muffinmad.myeteam.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by muffinmad on 02/11/2016.
 */

public class Hero implements Serializable {

    private String name;
    private ArrayList<Boolean> roles;
    private String skillLevel;
    private boolean carry;
    private boolean mid;
    private boolean offlane;
    private boolean support;
    private boolean jungleroam;

    public Hero() {
        carry = false;
        mid = false;
        offlane = false;
        support = false;
        jungleroam = false;
        skillLevel = "";
    }

    public Hero(String name, String skillLevel, boolean carry, boolean mid, boolean offlane, boolean support, boolean jungleroam) {
        this.name = name;
        this.skillLevel = skillLevel;
        this.carry = carry;
        this.mid = mid;
        this.offlane = offlane;
        this.support = support;
        this.jungleroam = jungleroam;
    }

    public Hero(String name, ArrayList<Boolean> roles, String skillLevel) {
        this.name = name;
        this.roles = roles;
        this.skillLevel = skillLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Boolean> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Boolean> roles) {
        this.roles = roles;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public boolean isCarry() {
        return carry;
    }

    public void setCarry(boolean carry) {
        this.carry = carry;
    }

    public boolean isMid() {
        return mid;
    }

    public void setMid(boolean mid) {
        this.mid = mid;
    }

    public boolean isOfflane() {
        return offlane;
    }

    public void setOfflane(boolean offlane) {
        this.offlane = offlane;
    }

    public boolean isSupport() {
        return support;
    }

    public void setSupport(boolean support) {
        this.support = support;
    }

    public boolean isJungleroam() {
        return jungleroam;
    }

    public void setJungleroam(boolean jungleroam) {
        this.jungleroam = jungleroam;
    }
}
