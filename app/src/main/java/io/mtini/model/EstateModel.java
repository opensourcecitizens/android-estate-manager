package io.mtini.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.mtini.android.tenantmanager.BR;

public class EstateModel extends BaseObservable implements Serializable, Cloneable{

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

    UUID id;
    String name;
    String address;
    TYPE type;
    String description;
    Integer tenantCount;
    String contacts;

    public EstateModel(UUID id, String name, String address, TYPE type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public EstateModel(UUID id, String name, String address, TYPE type, String contacts) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.contacts = contacts;
    }
    /*
    public EstateModel( String name, String address, TYPE type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }
    */

    @Bindable
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public Integer getTenantCount() {
        return tenantCount;
    }

    public void setTenantCount(Integer tenantCount) {
        this.tenantCount = tenantCount;
        notifyPropertyChanged(BR.tenantCount);
    }

    @Bindable
    public String getContacts(){return contacts;}

    public void setContacts(String contacts){this.contacts=contacts;
        notifyPropertyChanged(BR.contacts);}

    public static String[] getEstateTypeItems(){
        List<String> ret = new ArrayList(EstateModel.TYPE.values().length);

        for(EstateModel.TYPE type : EstateModel.TYPE.values()){
            ret.add(type.name());
        }

        return ret.toArray(new String[ret.size()]);
    }


    @Override
    protected EstateModel clone() throws CloneNotSupportedException {
        return (EstateModel)super.clone();
    }

    public EstateModel createClone(){
        try
        {
            return this.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("id").append(":").append(id)
                .append("name").append(":").append(name)
                .append("address").append(":").append(address)
                .append("type").append(":").append(type)
                .append("description").append(":").append(description)
                .append("contacts").append(":").append(contacts);

        return builder.toString();
    }

}
