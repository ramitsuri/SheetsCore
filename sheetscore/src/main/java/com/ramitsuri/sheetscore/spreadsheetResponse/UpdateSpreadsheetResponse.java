package com.ramitsuri.sheetscore.spreadsheetResponse;

import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;

public class UpdateSpreadsheetResponse extends BaseResponse {
    private BatchUpdateSpreadsheetResponse mBatchUpdateSpreadsheetResponse;

    public BatchUpdateSpreadsheetResponse getBatchUpdateSpreadsheetResponse() {
        return mBatchUpdateSpreadsheetResponse;
    }

    public void setBatchUpdateSpreadsheetResponse(
            BatchUpdateSpreadsheetResponse batchUpdateSpreadsheetResponse) {
        mBatchUpdateSpreadsheetResponse = batchUpdateSpreadsheetResponse;
    }
}
