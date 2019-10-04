package com.rppburhanpur.fuel2door;

import android.content.Context;
import android.content.SharedPreferences;

public class WelcomeActivityChecker {

    //This activity is responsible to check if first time user opens this app so
    // welcome activity should be run otherwise go directly on registration

    SharedPreferences pref; //get default sharedPreference
    SharedPreferences.Editor editor; //editable sharedPreference
    Context mcontext;

    int PRIVATE_MODE = 0; // make a private mode

    //defined static string variables;

    private static final String RELIENCE_PETROL_PUMP = "Your Welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";

    public WelcomeActivityChecker(Context context) {
        this.mcontext = context;
        pref = mcontext.getSharedPreferences(RELIENCE_PETROL_PUMP, PRIVATE_MODE);
        editor = pref.edit();
    }

    //set if user is first time or not in app launching
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }


    //first time always remains true
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}
