package com.ramitsuri.sheetscore;

import android.accounts.Account;
import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;

import java.util.List;

import androidx.annotation.NonNull;

class BaseProcessor {

    @NonNull
    private Context mContext;

    @NonNull
    private Account mAccount;

    @NonNull
    private List<String> mScopes;

    @NonNull
    String mAppName;

    BaseProcessor(@NonNull Context context, @NonNull String appName,
            @NonNull Account account, @NonNull List<String> scopes) {
        mContext = context;
        mAppName = appName;
        mAccount = account;
        mScopes = scopes;
    }

    GoogleAccountCredential getCredential() {
        GoogleAccountCredential credential = GoogleAccountCredential
                .usingOAuth2(mContext, mScopes)
                .setBackOff(new ExponentialBackOff());
        credential.setSelectedAccount(mAccount);
        return credential;
    }

    JacksonFactory getJacksonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    HttpTransport getHttpTransport() {
        return AndroidHttp.newCompatibleTransport();
    }
}
