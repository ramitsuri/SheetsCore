package com.ramitsuri.sheetscore.googleSignIn;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SignInResponse {
    private GoogleSignInAccount mGoogleSignInAccount;
    private Intent GoogleSignInIntent;

    public GoogleSignInAccount getGoogleSignInAccount() {
        return mGoogleSignInAccount;
    }

    public void setGoogleSignInAccount(
            GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
    }

    public Intent getGoogleSignInIntent() {
        return GoogleSignInIntent;
    }

    public void setGoogleSignInIntent(Intent googleSignInIntent) {
        GoogleSignInIntent = googleSignInIntent;
    }
}
