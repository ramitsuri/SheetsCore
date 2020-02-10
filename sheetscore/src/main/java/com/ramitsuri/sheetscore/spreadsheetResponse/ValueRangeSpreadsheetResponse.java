package com.ramitsuri.sheetscore.spreadsheetResponse;

import com.google.api.services.sheets.v4.model.ValueRange;

public class ValueRangeSpreadsheetResponse extends BaseResponse {
    private ValueRange mValueRange;

    public ValueRange getValueRange() {
        return mValueRange;
    }

    public void setValueRange(ValueRange valueRange) {
        mValueRange = valueRange;
    }
}
