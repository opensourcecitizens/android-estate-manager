package io.mtini.android.adaptor;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO deal with data formatting issue when adding decimal!
 *
 * */
public class TextFinancialBigDecimalBindingAdapter {

    public static final String regex = "(([1-9]+\\.?\\d*)|([0]\\.\\d*)|[0])";
    public static final Pattern pattern = Pattern.compile(regex);

    //setter
    @BindingAdapter({"bindFinancial"})
    public static void setFinancialValue(EditText view,
                                            final BigDecimal amount ){
        if(amount !=null) {
            String strVal = amount.toPlainString();
            if (view.getText() != null && !view.getText().toString().equals(strVal)) {
                view.setText(strVal);
            }
        }
    }

    //getter
    @InverseBindingAdapter(attribute="bindFinancial")
    public static BigDecimal getFinancialValue(EditText view){

        BigDecimal ret = null;
        String strVal =  view.getText().toString();
        if(view.getText()!=null) {
            //TODO verify matching patter to suite financial
            //TODO find a way to keep old valid value in pojo if text is invalid
            //Matcher matcher = pattern.matcher(strVal);
            //if (matcher.matches()) {
                try {
                    //ret = new BigDecimal(matcher.group(0));
                    ret = new BigDecimal(strVal);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    view.setError("Invalid data format");

                }
            //}
        }

        return ret;

    }

    //TODO find a way to pass this delay value as an attribute or value
    @BindingAdapter({"bindFinancialAttrChanged"})
    public static void setListener(EditText view, final InverseBindingListener listener) {

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

                        new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onChange();
                                }
                            },3000);

                    }
                });


            }


    }


}
