package com.ramitsuri.sheetscore.spreadsheetResponse;

import com.google.api.services.sheets.v4.model.Spreadsheet;

public class CreateSpreadsheetResponse extends BaseResponse {
    private Spreadsheet mResponse;

    public Spreadsheet getResponse() {
        return mResponse;
    }

    public void setResponse(Spreadsheet response) {
        mResponse = response;
    }
}
