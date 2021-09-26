package com.example.user.track.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MyPc on 09-09-2017.
 */

public class GlobalPreference {


    private SharedPreferences prefs;
    private Context context;
    SharedPreferences.Editor editor;

    public GlobalPreference(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void addIP(String ip) {
        editor.putString(Constants.IP, ip);
        editor.apply();

    }
    public void addCID(String id) {
        editor.putString(Constants.CID, id);
        editor.apply();

    }
    public String RetriveCID() {
        return prefs.getString(Constants.CID, "");
    }
    public void addUID(String id) {
        editor.putString(Constants.UID, id);
        editor.apply();

    }
    public String RetriveUID() {
        return prefs.getString(Constants.UID, "");
    }

    public String RetriveIP() {
        return prefs.getString(Constants.IP, "");
    }



    public void SaveCredentials(String name, String password, String phone) {
        editor.putString(Constants.NAME, name);
        editor.putString(Constants.PASSWORD, password);
        editor.putString(Constants.PHONE, phone);
        editor.commit();
    }

    public void SaveChampion(String cname, String speed) {
        editor.putString(Constants.CNAME, cname);
        editor.putString(Constants.SPEED, speed);
        editor.commit();
    }
    public String getCname() {
        return prefs.getString(Constants.CNAME, "");
    }

    public String getSpeeds()
    {
        return prefs.getString(Constants.SPEED, "");
    }
    public String getPhone() {
        return prefs.getString(Constants.PHONE, "");
    }



}
