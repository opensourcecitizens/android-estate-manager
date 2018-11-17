package io.mtini.model;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseMethod;
import android.nfc.Tag;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.mtini.android.adaptor.DatePickerBindingAdapter;
import io.mtini.android.tenantmanager.BR;
import io.mtini.android.tenantmanager.R;

public class TenantModel extends BaseObservable implements Serializable,Cloneable{

    public static enum STATUS {
        paid,late,balance,evicted;
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
            }
            return null;};
    };



    int id;
    int estateId;
    String name;
    String buildingNumber;
    String description;
    Date rentDueDate;
    STATUS status;
    BigDecimal rent;
    BigDecimal balance;
    String contacts;

    public TenantModel(int id, int estateId, String name, String buildingNumber, String description, Date rentDueDate, STATUS status, BigDecimal rent, BigDecimal balance, String contacts) {
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


    public TenantModel(int id, String name, String buildingNumber, String description, Date rentDueDate, STATUS status, BigDecimal rent, BigDecimal balance, String contacts) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstateId() {
        return estateId;
    }

    public void setEstateId(int estateId) {
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

    public static String[] getStatusItems(){
        List<String> ret = new ArrayList(STATUS.values().length);

        for(STATUS status : STATUS.values()){
            ret.add(status.name());
        }

        return ret.toArray(new String[ret.size()]);
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
