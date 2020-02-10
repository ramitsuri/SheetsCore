package com.ramitsuri.sheetscore.spreadsheetResponse;

import com.google.api.services.sheets.v4.model.Spreadsheet;

public class SpreadsheetSpreadsheetResponse extends BaseResponse {
    private Spreadsheet mSpreadsheet;

    public Spreadsheet getSpreadsheet() {
        return mSpreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        mSpreadsheet = spreadsheet;
    }
}
