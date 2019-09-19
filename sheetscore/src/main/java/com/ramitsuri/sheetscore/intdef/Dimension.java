package com.ramitsuri.sheetscore.intdef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

@StringDef({
        Dimension.DIMENSION_UNSPECIFIED,
        Dimension.ROWS,
        Dimension.COLUMNS
})
@Retention(RetentionPolicy.SOURCE)
public @interface Dimension {
    String DIMENSION_UNSPECIFIED = "DIMENSION_UNSPECIFIED";
    String ROWS = "ROWS";
    String COLUMNS = "COLUMNS";
}
