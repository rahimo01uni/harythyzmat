package com.eccomerce.com.main;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "HAYT_CHAT";
    // All Shared Preferences Keys
    // User name (make variable public to access from outside)
    public static final String KEY_ID = "chat_ID";
  //  public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
  //  public static final String KEY_EMAIL = "email";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createChatSession(String ID){
        // Storing camera in pref
        editor.putString(ID, ID);
        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
             * */
        public  String getChatCrediential(String KEY_){
            //Log.e("camera",pref.getString(KEY_CAMERA,"")+"");
            String CHATID = pref.getString(KEY_,null);
            return  CHATID;
        }

}