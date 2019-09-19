package com.ramitsuri.sheetscore.consumerResponse;

import java.util.List;

public class SheetsMetadataConsumerResponse extends BaseConsumerResponse {
    private List<SheetMetadata> mSheetMetadataList;

    public List<SheetMetadata> getSheetMetadataList() {
        return mSheetMetadataList;
    }

    public void setSheetMetadataList(List<SheetMetadata> sheetMetadataList) {
        mSheetMetadataList = sheetMetadataList;
    }

    @Override
    public String toString() {
        return "SheetsMetadataConsumerResponse{" +
                "mSheetMetadataList=" + mSheetMetadataList +
                "}\n" +
                super.toString();
    }
}
