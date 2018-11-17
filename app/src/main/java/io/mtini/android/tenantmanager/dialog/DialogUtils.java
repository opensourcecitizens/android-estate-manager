package io.mtini.android.tenantmanager.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {

    public static ProgressDialog startProgressDialog(Context context){

        ProgressDialog dialog = new ProgressDialog(context); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return dialog;
    }
}
