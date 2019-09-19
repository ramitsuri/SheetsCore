package com.ramitsuri.sheetscore.consumerResponse;

import java.util.List;

public class RangeConsumerResponse extends BaseConsumerResponse {

    private List<List<Object>> mObjectLists;

    public List<List<Object>> getObjectLists() {
        return mObjectLists;
    }

    public void setObjectLists(List<List<Object>> objectLists) {
        mObjectLists = objectLists;
    }

    @Override
    public String toString() {
        return "RangeConsumerResponse{" +
                "mObjectLists=" + mObjectLists +
                "}\n" +
                super.toString();
    }
}
