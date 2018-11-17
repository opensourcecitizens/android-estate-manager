package io.mtini.android.view;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

import io.mtini.android.tenantmanager.BR;
import io.mtini.model.TenantModel;

public class TenantViewModel extends BaseObservable implements Serializable,Cloneable{

    TenantModel tenant;

    public TenantViewModel(TenantModel model){
        this.tenant = model;
    }


    @Bindable
    public String getName(){
        return tenant.getName();
    }

    public void setName(String name){
        if(tenant.getName() != name) {
            tenant.setName(name);;
        }

        notifyPropertyChanged(BR.name);
    }



}
