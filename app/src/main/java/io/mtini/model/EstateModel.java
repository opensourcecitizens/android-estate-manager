package io.mtini.model;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class EstateModel extends BaseObservable implements Serializable{

    public static enum TYPE{condo, apartment, house, commercial;
    static TYPE getType(String s){
        switch(s) {
            case "condo":
                return condo;
            case "apartment":
                return apartment;
            case "house":
                return house;
            case "commercial":
                return commercial;
        }
        return null;};
    };

    Integer id;
    String name;
    String address;
    TYPE type;
    String description;
    Integer tenantCount;

    public EstateModel(Integer id, String name, String address, TYPE type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }
    /*
    public EstateModel( String name, String address, TYPE type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }
    */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTenantCount() {
        return tenantCount;
    }

    public void setTenantCount(Integer tenantCount) {
        this.tenantCount = tenantCount;
    }
}
