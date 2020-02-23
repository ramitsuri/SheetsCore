package com.ramitsuri.sheetscore.consumerResponse;

import java.util.List;

import javax.annotation.Nonnull;

public class RangesConsumerResponse extends BaseConsumerResponse {

    private List<RangeConsumerResponse> mValues;

    public List<RangeConsumerResponse> getValues() {
        return mValues;
    }

    public void setValues(List<RangeConsumerResponse> values) {
        mValues = values;
    }

    @Nonnull
    @Override
    public String toString() {
        return "RangeConsumerResponse{" +
                "mValues=" + mValues +
                "}\n" +
                super.toString();
    }
}
