package com.ramitsuri.sheetscore;

import android.accounts.Account;
import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.ramitsuri.sheetscore.intdef.Dimension;
import com.ramitsuri.sheetscore.intdef.ValueRenderOption;
import com.ramitsuri.sheetscore.spreadsheetResponse.BaseSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.SpreadsheetSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.UpdateSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.ValueRangeSpreadsheetResponse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import androidx.annotation.NonNull;

public class SheetsProcessor {

    @NonNull
    private Context mContext;
    @NonNull
    private String mAppName;
    @NonNull
    private String mSpreadsheetId;
    @NonNull
    private Account mAccount;
    @NonNull
    private List<String> mScopes;

    public SheetsProcessor(@NonNull Context context, @NonNull String appName,
            @NonNull Account account, @NonNull String spreadsheetId, @NonNull List<String> scopes) {
        mContext = context;
        mAppName = appName;
        mAccount = account;
        mSpreadsheetId = spreadsheetId;
        mScopes = scopes;
    }

    private Sheets buildSheetsService(
            @NonNull Context context,
            @NonNull String appName,
            @Nonnull Account account,
            @NonNull List<String> scopes) {
        GoogleAccountCredential credential = GoogleAccountCredential
                .usingOAuth2(context, scopes)
                .setBackOff(new ExponentialBackOff());
        credential.setSelectedAccount(account);
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Sheets.Builder(transport, jsonFactory, credential)
                .setApplicationName(appName)
                .build();
    }

    /**
     * Performs a "spreadsheet get" request on a given Spreadsheet
     */
    public BaseSpreadsheetResponse getSheetsInSpreadsheet()
            throws IOException {
        // Build Service
        Sheets service = buildSheetsService(mContext, mAppName, mAccount, mScopes);

        // Request
        Sheets.Spreadsheets.Get request = service.spreadsheets().get(mSpreadsheetId);
        request.setIncludeGridData(false);

        // Response
        Spreadsheet response = request.execute();
        SpreadsheetSpreadsheetResponse spreadsheetResponse = new SpreadsheetSpreadsheetResponse();
        spreadsheetResponse.setSpreadsheet(response);
        return spreadsheetResponse;
    }

    /**
     * Performs a "values get" request on a given Spreadsheet range
     */
    public BaseSpreadsheetResponse getSheetData(@NonNull String range, @Dimension String dimension)
            throws IOException {
        // Build Service
        Sheets service = buildSheetsService(mContext, mAppName, mAccount, mScopes);

        // Request
        Sheets.Spreadsheets.Values.Get request =
                service.spreadsheets().values().get(mSpreadsheetId, range);
        request.setValueRenderOption(ValueRenderOption.UNFORMATTED_VALUE);
        request.setMajorDimension(dimension);

        // Response
        ValueRange response = request.execute();
        ValueRangeSpreadsheetResponse spreadsheetResponse = new ValueRangeSpreadsheetResponse();
        spreadsheetResponse.setValueRange(response);
        return spreadsheetResponse;
    }

    /**
     * Performs a "batchUpdate" request on a given Spreadsheet
     */
    public BaseSpreadsheetResponse updateSheet(@NonNull BatchUpdateSpreadsheetRequest requestBody)
            throws IOException {
        // Build Service
        Sheets service = buildSheetsService(mContext, mAppName, mAccount, mScopes);

        // Request
        Sheets.Spreadsheets.BatchUpdate request =
                service.spreadsheets().batchUpdate(mSpreadsheetId, requestBody);

        // Response
        BatchUpdateSpreadsheetResponse response = request.execute();
        UpdateSpreadsheetResponse spreadsheetResponse = new UpdateSpreadsheetResponse();
        spreadsheetResponse.setBatchUpdateSpreadsheetResponse(response);
        return spreadsheetResponse;
    }
}
