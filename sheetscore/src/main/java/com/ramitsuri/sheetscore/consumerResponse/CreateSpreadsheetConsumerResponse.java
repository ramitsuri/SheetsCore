package com.ramitsuri.sheetscore.consumerResponse;

import java.util.List;

import javax.annotation.Nonnull;

public class CreateSpreadsheetConsumerResponse extends BaseConsumerResponse {

    private String mSpreadsheetId;
    private List<SheetMetadata> mSheetMetadataList;

    public String getSpreadsheetId() {
        return mSpreadsheetId;
    }

    public CreateSpreadsheetConsumerResponse setSpreadsheetId(String spreadsheetId) {
        mSpreadsheetId = spreadsheetId;
        return this;
    }

    public List<SheetMetadata> getSheetMetadataList() {
        return mSheetMetadataList;
    }

    public void setSheetMetadataList(List<SheetMetadata> sheetMetadataList) {
        mSheetMetadataList = sheetMetadataList;
    }

    @Nonnull
    @Override
    public String toString() {
        return "CreateSpreadsheetConsumerResponse{" +
                "mSpreadsheetId='" + mSpreadsheetId + '\'' +
                ", mSheetMetadataList=" + mSheetMetadataList +
                '}';
    }
}
