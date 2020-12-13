package com.ramitsuri.sheetscore.consumerResponse;

public class AddSheetConsumerResponse extends BaseConsumerResponse {
    private SheetMetadata mSheetMetadata;

    public SheetMetadata getSheetMetadata() {
        return mSheetMetadata;
    }

    public void setSheetMetadata(SheetMetadata sheetMetadata) {
        mSheetMetadata = sheetMetadata;
    }

    @Override
    public String toString() {
        return "AddSheetResponse{" +
                "mSheetMetadata=" + mSheetMetadata +
                '}';
    }
}
