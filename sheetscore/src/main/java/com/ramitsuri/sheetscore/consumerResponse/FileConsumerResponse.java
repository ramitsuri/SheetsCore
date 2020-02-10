package com.ramitsuri.sheetscore.consumerResponse;

import javax.annotation.Nonnull;

public class FileConsumerResponse extends BaseConsumerResponse {

    private String mFileId;

    public String getFileId() {
        return mFileId;
    }

    public void setFileId(String fileId) {
        mFileId = fileId;
    }

    @Override
    @Nonnull
    public String toString() {
        return "FileConsumerResponse{" +
                "mFileId='" + mFileId + '\'' +
                "}" +
                super.toString();
    }
}
