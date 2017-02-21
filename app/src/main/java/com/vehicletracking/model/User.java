package com.vehicletracking.model;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by admin on 2/20/2017.
 */

public class User  implements Serializable{
    public static final String LOGIN_METHOD_NORMAL = "NORMAL";
    public static final String LOGIN_METHOD_GOOGLE = "GOOGLE";
    public static final String INTENT_NAME = "USER";
    private String email;
    private String username;
    private String LOGIN_METHOD ;
    private String profilePicture;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginMethod() {
        return LOGIN_METHOD;
    }

    public void setLoginMethod(String loginMethod) {
        this.LOGIN_METHOD = loginMethod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}


