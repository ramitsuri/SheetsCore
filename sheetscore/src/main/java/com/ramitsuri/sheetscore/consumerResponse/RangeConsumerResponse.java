package com.ramitsuri.sheetscore.consumerResponse;

import java.util.List;

import javax.annotation.Nonnull;

public class RangeConsumerResponse extends BaseConsumerResponse {

    private String mRange;
    private List<List<Object>> mObjectLists;

    public String getRange() {
        return mRange;
    }

    public RangeConsumerResponse setRange(String range) {
        mRange = range;
        return this;
    }

    public List<List<Object>> getObjectLists() {
        return mObjectLists;
    }

    public void setObjectLists(List<List<Object>> objectLists) {
        mObjectLists = objectLists;
    }

    @Nonnull
    @Override
    public String toString() {
        return "RangeConsumerResponse{" +
                "mRange='" + mRange + '\'' +
                ", mObjectLists=" + mObjectLists +
                "}";
    }
}
