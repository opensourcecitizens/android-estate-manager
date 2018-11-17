package io.mtini.android.adaptor;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Build;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import io.mtini.android.tenantmanager.R;

public final class SpinnerBindingAdapter {

    private SpinnerBindingAdapter() {}

/*
    @BindingAdapter({"dropDownItemList", "selectedItemPosition"})
    public static void setForumGroupNameList(Spinner spinner, List<CharSequence> dropDownItemList, int selectedItemPosition) {
        spinner.setAdapter(getSpinnerAdapter(spinner, dropDownItemList));
        // invalid position may occurs when user's login status has changed
        if (spinner.getAdapter().getCount() - 1 < selectedItemPosition) {
            spinner.setSelection(0, false);
        } else {
            spinner.setSelection(selectedItemPosition, false);
        }
    }

    private static BaseAdapter getSpinnerAdapter(Spinner spinner, List<CharSequence> dropDownItemList) {
        // don't use dropDownItemList#add(int, E)
        // otherwise we will have multiple "全部"
        // if we call this method many times

        List<CharSequence> list = new ArrayList<>();
        // the first drop-down item is "全部"
        // and other items fetched from S1
        list.add(spinner.getContext().getString(
                R.string.toolbar_spinner_drop_down_all_forums_item_title));
        list.addAll(dropDownItemList);

        ArrayAdapter<CharSequence> arrayAdapter;
        Context context = spinner.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            arrayAdapter = new ArrayAdapter(context, R.layout.toolbar_spinner_item, list);
        } else {
            arrayAdapter = new ArrayAdapterCompat<>(context, R.layout.toolbar_spinner_item, list);
        }
        arrayAdapter.setDropDownViewResource(R.layout.toolbar_spinner_dropdown_item);

        return arrayAdapter;
    }*/

    //SETTER
    @BindingAdapter(value = {"app:selectedSpinnerValue", "app:selectedSpinnerValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(Spinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {

        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(newTextAttrChanged!=null)
                    newTextAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
            pAppCompatSpinner.setSelection(pos, true);
        }
    }

    //GETTER
    @InverseBindingAdapter(attribute = "app:selectedSpinnerValue", event = "app:selectedSpinnerValueAttrChanged")
    public static String captureSelectedValue(Spinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.getSelectedItem();
    }

}

