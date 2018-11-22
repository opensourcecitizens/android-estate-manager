package io.mtini.android.tenantmanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import io.mtini.android.tenantmanager.R;

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


    public static AlertDialog startErrorDialog(Context context, String message){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);// this = YourActivity
        //TODO add icon dialogBuilder.setIcon()
        dialogBuilder.setMessage(message)
                .setTitle("Error");
        dialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //dismiss();
            }
        });

        AlertDialog errorDialog = dialogBuilder.create();
        errorDialog.show();
        return errorDialog;
    }

}
