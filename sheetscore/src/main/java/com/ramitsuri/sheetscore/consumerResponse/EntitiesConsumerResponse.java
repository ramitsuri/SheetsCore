package com.ramitsuri.sheetscore.consumerResponse;

import java.util.List;

public class EntitiesConsumerResponse extends BaseConsumerResponse {

    public static final int MAX_ENTITIES = 3;

    private List<List<String>> mStringLists;

    public List<List<String>> getStringLists() {
        return mStringLists;
    }

    public void setStringLists(List<List<String>> stringLists) {
        mStringLists = stringLists;
    }

    @Override
    public String toString() {
        return "EntitiesConsumerResponse{" +
                "mStringLists=" + mStringLists +
                "}\n" +
                super.toString();
    }
}
