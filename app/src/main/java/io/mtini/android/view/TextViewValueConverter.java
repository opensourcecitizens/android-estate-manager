package io.mtini.android.view;

import android.content.res.Resources;
import android.databinding.BindingMethod;
import android.databinding.InverseMethod;
import android.databinding.BindingConversion;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.mtini.model.TenantModel;

public class TextViewValueConverter {

    @InverseMethod("statusToStr")
    static public TenantModel.STATUS toStatus(String status){

        TenantModel.STATUS ret = TenantModel.STATUS.valueOf(status);

        return ret;
    }

    static public String statusToStr(TenantModel.STATUS status){

        String ret = null;

        //is view same as date?
        //else set date
        //on error set olddate
        if(status==null)return null;
        ret = status.name();

        return ret;
    }



    @InverseMethod("toDate")
    static public Date stringToDate(TextView  view, String date){

        Date ret = null;

        try {
            if( view.getText()!=null && date!=null && !view.getText().equals(date)) {
                ret = dateFormat.parse(date);
            }
        }catch(ParseException e){

            Resources resources = view.getResources();
            String errStr = "Bad data format; expected "+DATE_PATTERN;//resources.getString(R.string.badNumber);
            view.setError(errStr);

            //ret = olddate;
        }


        return ret;
    }

    static public String toDate(TextView  view,Date date){

        String ret = null;

        //is view same as date?
            //else set date
            //on error set olddate
        if(date==null)return null;
        ret = dateFormat.format(date);

        return ret;
    }

    //static String[] patterns = new String[]{"yyyy/MM/dd", "yyyy/MM/dd"};
    public static final String DATE_PATTERN = "yyyy/MM/dd";
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
}
