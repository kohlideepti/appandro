package com.vehicletracking.googleConstants;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by admin on 2/20/2017.
 */

public class GoogleClient {

    private static final GoogleClient gc = new GoogleClient();
    private GoogleSignInAccount account ;


    private GoogleClient(){

    }


    public static void setInstanceVariables(GoogleSignInAccount account){
        gc.account = account;
    }

    public static GoogleClient getInstance(){
        return gc;
    }


    public GoogleSignInAccount getAccount() {
        return account;
    }




}
