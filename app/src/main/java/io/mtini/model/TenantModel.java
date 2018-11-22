package io.mtini.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.mtini.android.tenantmanager.BR;

public class TenantModel extends BaseObservable implements Serializable,Cloneable{

    public static enum STATUS {
        paid,late,balance,evicted,new_tenant;
        static STATUS getStatus(String s){
            switch(s) {
                case "paid":
                    return paid;
                case "late":
                    return late;
                case "balance":
                    return balance;
                case "evicted":
                    return evicted;
                case "new_tenant":
                    return new_tenant;
            }
            return null;};
    };



    UUID id;
    UUID estateId;
    String name;
    String buildingNumber;
    String description;
    Date rentDueDate;
    STATUS status;
    BigDecimal rent;
    BigDecimal balance;
    String contacts;

    public TenantModel(UUID id, UUID estateId, String name, String buildingNumber, String description, Date rentDueDate, STATUS status, BigDecimal rent, BigDecimal balance, String contacts) {
        this.id = id;
        this.estateId=estateId;
        this.name = name;
        this.buildingNumber = buildingNumber;
        this.description = description;
        this.rentDueDate = rentDueDate;
        this.status = status;
        this.rent = rent;
        this.balance = balance;
        this.contacts = contacts;
    }


    public TenantModel(UUID id, String name, String buildingNumber, String description, Date rentDueDate, STATUS status, BigDecimal rent, BigDecimal balance, String contacts) {
        this.id = id;
        this.name = name;
        this.buildingNumber = buildingNumber;
        this.description = description;
        this.rentDueDate = rentDueDate;
        this.status = status;
        this.rent = rent;
        this.balance = balance;
        this.contacts = contacts;
    }

    public TenantModel(String name, String buildingNumber, String description, Date rentDueDate, STATUS status, BigDecimal rent, BigDecimal balance, String contacts) {
        this.name = name;
        this.buildingNumber = buildingNumber;
        this.description = description;
        this.rentDueDate = rentDueDate;
        this.status = status;
        this.rent = rent;
        this.balance = balance;
        this.contacts = contacts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEstateId() {
        return estateId;
    }

    public void setEstateId(UUID estateId) {
        this.estateId = estateId;
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
    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        notifyPropertyChanged(BR.buildingNumber);
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
    public Date getRentDueDate() {
        return rentDueDate;
    }

    public void setRentDueDate(Date rentDueDate) {
        this.rentDueDate = rentDueDate;
        notifyPropertyChanged(BR.rentDueDate);
    }

    @Bindable
    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
        notifyPropertyChanged(BR.rent);
    }

    @Bindable
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        notifyPropertyChanged(BR.balance);
    }

    @Bindable
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        if(this.contacts!=null
                && !this.contacts.equals(contacts)) {
            this.contacts = contacts;
            notifyPropertyChanged(BR.contacts);
        }
    }

    public static String[] getStatusItems(){
        List<String> ret = new ArrayList(STATUS.values().length);

        for(STATUS status : STATUS.values()){
            ret.add(status.name());
        }

        return ret.toArray(new String[ret.size()]);
    }

    @Override
    protected TenantModel clone() throws CloneNotSupportedException {
        return (TenantModel)super.clone();
    }

    public TenantModel createClone(){
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
        builder = builder.append("id").append(id).append("\n").
                append("estateId").append(estateId).append("\n").
                append("name").append(name).append("\n").
                append("buildingNumber").append(buildingNumber).append("\n").
                append("description").append(description).append("\n").
                append("rentDueDate").append(rentDueDate).append("\n").
                append("status").append(status).append("\n").
                append("rent").append(rent).append("\n").
                append("balance").append(balance).append("\n").
                append("contacts").append(contacts).append("\n");


        return builder.toString();
    }


}
