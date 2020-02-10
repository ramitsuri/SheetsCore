package com.ramitsuri.sheetscore.driveResponse;

import com.google.api.services.drive.model.File;
import com.ramitsuri.sheetscore.spreadsheetResponse.BaseResponse;

public class FileCopyResponse extends BaseResponse {
    private File mResponse;

    public File getResponse() {
        return mResponse;
    }

    public void setResponse(File response) {
        mResponse = response;
    }
}
