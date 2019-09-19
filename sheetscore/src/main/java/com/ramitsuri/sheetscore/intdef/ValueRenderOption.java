package com.ramitsuri.sheetscore.intdef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

@StringDef({
        ValueRenderOption.FORMATTED_VALUE,
        ValueRenderOption.UNFORMATTED_VALUE,
        ValueRenderOption.FORMULA
})
@Retention(RetentionPolicy.SOURCE)
public @interface ValueRenderOption {
    String FORMATTED_VALUE = "FORMATTED_VALUE";
    String UNFORMATTED_VALUE = "UNFORMATTED_VALUE";
    String FORMULA = "FORMULA";
}
