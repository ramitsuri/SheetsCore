package com.ramitsuri.sheetscore.consumerResponse;

public class SheetMetadata {
    private int mSheetId;
    private String mSheetName;

    public SheetMetadata(int sheetId, String sheetName) {
        mSheetId = sheetId;
        mSheetName = sheetName;
    }

    @Override
    public String toString() {
        return "SheetMetadata{" +
                "mSheetId=" + mSheetId +
                ", mSheetName='" + mSheetName + '\'' +
                "}\n";
    }
}
