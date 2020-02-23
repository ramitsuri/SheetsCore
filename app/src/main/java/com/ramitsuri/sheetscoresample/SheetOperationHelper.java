package com.ramitsuri.sheetscoresample;

import android.accounts.Account;
import android.net.Uri;
import android.util.Log;

import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.ramitsuri.sheetscore.DriveProcessor;
import com.ramitsuri.sheetscore.SheetsProcessor;
import com.ramitsuri.sheetscore.driveResponse.FileCopyResponse;
import com.ramitsuri.sheetscore.intdef.Dimension;
import com.ramitsuri.sheetscore.spreadsheetResponse.BaseResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.CreateSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.SpreadsheetSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.ValueRangeSpreadsheetResponse;
import com.ramitsuri.sheetscore.spreadsheetResponse.ValueRangesSpreadsheetResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import androidx.core.util.Pair;

import static com.ramitsuri.sheetscoresample.Constants.SCOPES;

public class SheetOperationHelper {

    private static final String TAG = SheetOperationHelper.class.getName();

    public static void openFileFromFilePicker(Uri uri, Account account) {
        DriveProcessor processor =
                new DriveProcessor(MainApplication.getInstance(),
                        MainApplication.getInstance().getApplicationInfo().name,
                        account,
                        Arrays.asList(SCOPES));
        Log.d(TAG, "Opening " + uri.getPath());

        Pair<String, String> nameAndContent =
                processor.openFileUsingStorageAccessFramework(
                        MainApplication.getInstance().getContentResolver(), uri);

        String name = nameAndContent.first;
        String content = nameAndContent.second;
        Log.d(TAG, "Opening " + name + content);
    }

    public static void copy(@Nonnull final Account account, final String fileId, String fileName) {
        final File request = new File();
        request.setName(fileName);
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                DriveProcessor processor =
                        new DriveProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    FileCopyResponse response =
                            (FileCopyResponse)processor.copyFile(fileId, request);
                    Log.d(TAG, response.getResponse().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getSheets(@Nonnull final Account account, final String spreadsheetId) {
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                SheetsProcessor processor =
                        new SheetsProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    SpreadsheetSpreadsheetResponse response =
                            (SpreadsheetSpreadsheetResponse)processor
                                    .getSheetsInSpreadsheet(spreadsheetId);
                    Log.d(TAG, response.getSpreadsheet().getSheets().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getRange(@Nonnull final Account account, final String spreadsheetId,
            final String range) {
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                SheetsProcessor processor =
                        new SheetsProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    BaseResponse response =
                            processor.getSheetData(spreadsheetId, range, Dimension.ROWS);
                    List<List<Object>> objectLists =
                            ((ValueRangeSpreadsheetResponse)response).getValueRange()
                                    .getValues();
                    if (objectLists != null) {
                        Log.d(TAG, objectLists.toString());
                    } else {
                        Log.d(TAG, "Range response - object list null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getRanges(@Nonnull final Account account, final String spreadsheetId,
            final List<String> ranges) {
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                SheetsProcessor processor =
                        new SheetsProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    BaseResponse response =
                            processor.getSheetData(spreadsheetId, ranges, Dimension.ROWS);
                    for (ValueRange range : ((ValueRangesSpreadsheetResponse)response)
                            .getValueRanges()) {
                        Log.d(TAG, "------------------------------");
                        if (range.getRange() != null && range.getValues() != null) {
                            Log.d(TAG, range.getRange());
                            Log.d(TAG, range.getValues().toString());
                        }
                        Log.d(TAG, "------------------------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void insertRange(@Nonnull final Account account, final String spreadsheetId,
            final String row, final int sheetId) {
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                SheetsProcessor processor =
                        new SheetsProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    BatchUpdateSpreadsheetRequest requestBody =
                            SpreadsheetRequestHelper.getUpdateRequestBody(row, sheetId);
                    processor.updateSheet(spreadsheetId, requestBody);
                    Log.d(TAG, "Insert success");
                } catch (IOException e) {
                    Log.d(TAG, "Insert unsuccess");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void createSpreadsheet(@Nonnull final Account account,
            @Nonnull final String spreadsheetTitle,
            @Nonnull final String entitiesSheetTitle,
            final int entitiesSheetIndex,
            @Nonnull final String templateSheetTitle,
            final int templateSheetIndex,
            @Nonnull final List<String> paymentMethods,
            @Nonnull final List<String> categories,
            @Nonnull final List<String> months) {
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                SheetsProcessor processor =
                        new SheetsProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    Spreadsheet requestBody = SpreadsheetRequestHelper
                            .getCreateRequest(spreadsheetTitle, entitiesSheetTitle,
                                    entitiesSheetIndex, templateSheetTitle, templateSheetIndex,
                                    paymentMethods, categories);
                    BaseResponse response = processor.createSheet(requestBody);
                    Log.d(TAG,
                            ((CreateSpreadsheetResponse)response).getResponse().getSpreadsheetId());
                } catch (IOException e) {
                    Log.d(TAG, "Create unsuccess");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void duplicateSheets(@Nonnull final Account account,
            @Nonnull final String spreadsheetId,
            final int sourceSheetId,
            final int startIndex,
            @Nonnull final List<String> months) {
        AppExecutors executors = AppExecutors.getInstance();
        executors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                SheetsProcessor processor =
                        new SheetsProcessor(MainApplication.getInstance(),
                                MainApplication.getInstance().getApplicationInfo().name,
                                account,
                                Arrays.asList(SCOPES));
                try {
                    BatchUpdateSpreadsheetRequest requestBody = SpreadsheetRequestHelper
                            .getDuplicateSheetsRequest(sourceSheetId, startIndex,
                                    months);
                    processor.updateSheet(spreadsheetId, requestBody);
                    Log.d(TAG, "duplicate sheets success");
                } catch (IOException e) {
                    Log.d(TAG, "duplicate sheets unsuccess");
                    e.printStackTrace();
                }
            }
        });
    }
}
