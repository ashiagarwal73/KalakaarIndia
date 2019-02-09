package com.agarwal.ashi.kalakaarindia.Utility;

import com.agarwal.ashi.kalakaarindia.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDetails {
    private static FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    private static User appUser;

    public static User getAppUser() {
        return appUser;
    }

    public static void setAppUser(User appUser) {
        UserDetails.appUser = appUser;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static void setUser(FirebaseUser user) {
        UserDetails.user = user;
    }
}
