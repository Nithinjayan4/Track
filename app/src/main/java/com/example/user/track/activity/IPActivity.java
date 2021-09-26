package com.example.user.track.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.user.track.R;
import com.example.user.track.permisions.BaseActivity;
import com.example.user.track.service.notification_service;
import com.example.user.track.service.wifi_service;
import com.example.user.track.util.GlobalPreference;


public class IPActivity extends BaseActivity {

    final Context c = this;
    GlobalPreference mGlobalPreference;
    public static final String TAG=IPActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        mGlobalPreference= new GlobalPreference(getApplicationContext());
        getIP();
    }




    public void getIP(){


        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);
        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        userInputDialogEditText.setText(mGlobalPreference.RetriveIP());

        alertDialogBuilderUserInput
                .setTitle("    Enter Your IP Address    ")

                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        mGlobalPreference.addIP(userInputDialogEditText.getText().toString());

                        userInputDialogEditText.setText(userInputDialogEditText.getText().toString());
                        Intent in=new Intent(getApplicationContext(),notification_service.class);
                        startService(in);
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));


                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }
}
