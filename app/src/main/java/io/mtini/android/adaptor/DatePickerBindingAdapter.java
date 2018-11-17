package io.mtini.android.adaptor;

import android.app.DatePickerDialog;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerBindingAdapter {

    public static final String DATE_PATTERN = "yyyy/MM/dd";
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

    //setter
    @BindingAdapter("bindDate")
    public static void setDateValue(TextView view,
                                            final Date newDate ){

            if(newDate!=null) {
                String newDateStr = dateFormat.format(newDate);
                view.setText(newDateStr);
            }

    }

    //getter
    @InverseBindingAdapter(attribute="bindDate")
    public static Date getPhoneContactValue(TextView view){
        Date newDate = null;
        if(view.getText()!=null) {
            String newDateStr = view.getText().toString();
            try {
                newDate = dateFormat.parse(newDateStr);
            } catch (ParseException e) {
                view.setError("Invalid date format. Expected "+DATE_PATTERN);
            }

        }
        return newDate;
    }


    @BindingAdapter(value = "bindDateAttrChanged")
    public static void setChangeListener(TextView view, final InverseBindingListener listener) {
        if (listener != null) {
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    listener.onChange();
                }
            });
        }
    }

    public static void onClickDate( View view, final Date currentDate) {

        final TextView textView = (TextView)view;
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(currentDate==null?new Date(): currentDate);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);

                DatePickerBindingAdapter.setDateValue(textView, calendar.getTime());

            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

}
