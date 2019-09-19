package com.ramitsuri.sheetscore.consumerResponse;

public class InsertConsumerResponse extends BaseConsumerResponse {

    private boolean mIsSuccessful;

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

    public void setSuccessful(boolean successful) {
        mIsSuccessful = successful;
    }

    @Override
    public String toString() {
        return "InsertConsumerResponse{" +
                "mIsSuccessful=" + mIsSuccessful +
                "}\n" +
                super.toString();
    }
}
