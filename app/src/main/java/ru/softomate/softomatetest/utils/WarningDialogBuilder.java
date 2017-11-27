package ru.softomate.softomatetest.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ru.softomate.softomatetest.R;

/**
 * Created by Вараздат on 26.11.2017.
 */

public class WarningDialogBuilder {


    private Context mContext;
    private int mMessage;


    public  WarningDialogBuilder(Context context){
        mContext = context;
    };



    public WarningDialogBuilder message(int message){
        mMessage = message;
        return  this;
    }

    public AlertDialog build(){
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.warning)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .setMessage(mMessage)
                .create();
        return alertDialog;
    }



}
