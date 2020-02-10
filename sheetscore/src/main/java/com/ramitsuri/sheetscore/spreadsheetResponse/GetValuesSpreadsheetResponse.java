package com.ramitsuri.sheetscore.spreadsheetResponse;

import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;

public class GetValuesSpreadsheetResponse extends BaseResponse {
    private BatchGetValuesResponse mBatchGetValuesResponse;

    public BatchGetValuesResponse getBatchGetValuesResponse() {
        return mBatchGetValuesResponse;
    }

    public void setBatchGetValuesResponse(
            BatchGetValuesResponse batchGetValuesResponse) {
        mBatchGetValuesResponse = batchGetValuesResponse;
    }
}
