package com.ramitsuri.sheetscore;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.ramitsuri.sheetscore.driveResponse.FileCopyResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.BaseResponse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

public class DriveProcessor extends BaseProcessor {

    public DriveProcessor(@NonNull Context context,
            @NonNull String appName,
            @NonNull Account account,
            @NonNull List<String> scopes) {
        super(context, appName, account, scopes);
    }

    private Drive buildService(@NonNull String appName) {
        return new Drive.Builder(getHttpTransport(), getJacksonFactory(), getCredential())
                .setApplicationName(appName)
                .build();
    }

    /**
     * Creates a copy of given source file
     */
    public BaseResponse copyFile(@NonNull String sourceFileId, @NonNull File requestBody)
            throws IOException {
        // Build Service
        Drive service = buildService(mAppName);

        // Request
        Drive.Files.Copy request =
                service.files().copy(sourceFileId, requestBody);

        // Response

        File response = request.execute();
        FileCopyResponse fileCopyResponse = new FileCopyResponse();
        fileCopyResponse.setResponse(response);
        return fileCopyResponse;
    }

    public Pair<String, String> openFileUsingStorageAccessFramework(
            final ContentResolver contentResolver, final Uri uri) {
        // Retrieve the document's display name from its metadata.
        String name = null;
        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                name = cursor.getString(nameIndex);
            } else {
                //throw new IOException("Empty cursor returned for file.");
            }
        }

        // Read the document's contents as a String.
        String content = null;
        try (InputStream is = contentResolver.openInputStream(uri);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            content = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Pair.create(name, content);
    }

    /**
     * Returns an {@link Intent} for opening the Storage Access Framework file picker.
     */
    public Intent createFilePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.google-apps.spreadsheet");

        return intent;
    }
}
