package com.ramitsuri.sheetscore.spreadsheetResponse;

import com.google.api.services.sheets.v4.model.ValueRange;

import java.util.List;

public class ValueRangesSpreadsheetResponse extends BaseResponse {
    private List<ValueRange> mValueRanges;

    public List<ValueRange> getValueRanges() {
        return mValueRanges;
    }

    public void setValueRanges(List<ValueRange> valueRanges) {
        mValueRanges = valueRanges;
    }
}
