package com.ramitsuri.sheetscore.consumerResponse;

public class SheetMetadata {
    private int mSheetId;
    private String mSheetName;

    public SheetMetadata(int sheetId, String sheetName) {
        mSheetId = sheetId;
        mSheetName = sheetName;
    }

    public int getSheetId() {
        return mSheetId;
    }

    public void setSheetId(int sheetId) {
        mSheetId = sheetId;
    }

    public String getSheetName() {
        return mSheetName;
    }

    public void setSheetName(String sheetName) {
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
