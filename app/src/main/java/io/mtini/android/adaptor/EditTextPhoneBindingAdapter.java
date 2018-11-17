package io.mtini.android.adaptor;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextPhoneBindingAdapter {

    //setter
    @BindingAdapter("bindPhone")
    public static void setPhoneContactValue(EditText view,
                                            final String phoneText ){
        Object tag = view.getTag();
        if(view.getText()!=null && !view.getText().toString().equals(phoneText)){
            view.setText(phoneText);
        }
    }

    //getter
    @InverseBindingAdapter(attribute="bindPhone")
    public static String getPhoneContactValue(EditText view){
        String ret =  view.getText().toString();
        //TODO perform formating to add or remove () - / and validate

        return ret;
    }


    @BindingAdapter(value = "bindPhoneAttrChanged")
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
                    },2000);
                }
            });
        }
    }
}
