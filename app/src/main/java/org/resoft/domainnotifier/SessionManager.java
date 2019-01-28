package org.resoft.domainnotifier;

/**
 * Created by onuragtas on 15.07.2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by onuragtas on 5.02.2017.
 */

public class SessionManager {

    private final Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        editor = preferences.edit();
    }

    public void setDomain(String domain) throws JSONException {
        String domains = preferences.getString("domains",null);
        JSONArray json;
        if(domains != null) {
            json = new JSONArray(domains);
        }else{
            json = new JSONArray();
        }
        json.put(domain);
        editor.putString("domains",json.toString()).commit();
    }

    public ArrayList<String> getDomains() throws JSONException {
        String domains = preferences.getString("domains",null);
        ArrayList<String> list = new ArrayList<>();
        if(domains != null) {
            JSONArray json = new JSONArray(domains);
            list = new ArrayList<>();
            for (int i = 0; i < json.length(); i++) {
                list.add(json.getString(i));
            }
        }
        return list;
    }

    public void directSaveDomains(String j){
        editor.putString("domains",j).commit();
    }

    public ArrayList<String> deleteDomain(int index) throws JSONException {
        String domains = preferences.getString("domains",null);
        JSONArray json = new JSONArray(domains);
        JSONArray newjson = new JSONArray();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            if(index != i) {
                newjson.put(json.getString(i));
                list.add(json.getString(i));
            }
        }

        directSaveDomains(newjson.toString());

        return list;
    }

}