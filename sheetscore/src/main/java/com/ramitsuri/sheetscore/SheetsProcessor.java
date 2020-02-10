package com.ramitsuri.sheetscore;

import android.accounts.Account;
import android.content.Context;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.ramitsuri.sheetscore.intdef.Dimension;
import com.ramitsuri.sheetscore.intdef.ValueRenderOption;
import com.ramitsuri.sheetscore.spreadsheetResponse.BaseResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.CreateSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.SpreadsheetSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.UpdateSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.ValueRangeSpreadsheetResponse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;

import androidx.annotation.NonNull;

public class SheetsProcessor extends BaseProcessor {

    public SheetsProcessor(@NonNull Context context, @NonNull String appName,
            @NonNull Account account, @NonNull List<String> scopes) {
        super(context, appName, account, scopes);
    }

    private Sheets buildService(@NonNull String appName) {
        return new Sheets.Builder(getHttpTransport(), getJacksonFactory(), getCredential())
                .setApplicationName(appName)
                .build();
    }

    /**
     * Performs a "spreadsheet get" request on a given Spreadsheet
     */
    public BaseResponse getSheetsInSpreadsheet(@Nonnull String spreadsheetId)
            throws IOException {
        // Build Service
        Sheets service = buildService(mAppName);

        // Request
        Sheets.Spreadsheets.Get request = service.spreadsheets().get(spreadsheetId);
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
    public BaseResponse getSheetData(@Nonnull String spreadsheetId,
            @NonNull String range,
            @Dimension String dimension)
            throws IOException {
        // Build Service
        Sheets service = buildService(mAppName);

        // Request
        Sheets.Spreadsheets.Values.Get request =
                service.spreadsheets().values().get(spreadsheetId, range);
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
    public BaseResponse updateSheet(@Nonnull String spreadsheetId,
            @NonNull BatchUpdateSpreadsheetRequest requestBody)
            throws IOException {
        // Build Service
        Sheets service = buildService(mAppName);

        // Request
        Sheets.Spreadsheets.BatchUpdate request =
                service.spreadsheets().batchUpdate(spreadsheetId, requestBody);

        // Response
        BatchUpdateSpreadsheetResponse response = request.execute();
        UpdateSpreadsheetResponse spreadsheetResponse = new UpdateSpreadsheetResponse();
        spreadsheetResponse.setBatchUpdateSpreadsheetResponse(response);
        return spreadsheetResponse;
    }

    /**
     * Performs a "create" request on a given Spreadsheet
     */
    public BaseResponse createSheet(@NonNull Spreadsheet requestBody)
            throws IOException {
        // Build Service
        Sheets service = buildService(mAppName);

        // Request
        Sheets.Spreadsheets.Create request =
                service.spreadsheets().create(requestBody);

        // Response
        Spreadsheet response = request.execute();
        CreateSpreadsheetResponse spreadsheetResponse = new CreateSpreadsheetResponse();
        spreadsheetResponse.setResponse(response);
        return spreadsheetResponse;
    }
}
