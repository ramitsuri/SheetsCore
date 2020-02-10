package com.ramitsuri.sheetscoresample;

import android.text.TextUtils;

import com.google.api.services.sheets.v4.model.AppendCellsRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BooleanCondition;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.CellFormat;
import com.google.api.services.sheets.v4.model.Color;
import com.google.api.services.sheets.v4.model.ConditionValue;
import com.google.api.services.sheets.v4.model.DataValidationRule;
import com.google.api.services.sheets.v4.model.DuplicateSheetRequest;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridData;
import com.google.api.services.sheets.v4.model.GridProperties;
import com.google.api.services.sheets.v4.model.Padding;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.TextFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Nonnull;

public class SpreadsheetRequestHelper {

    public static BatchUpdateSpreadsheetRequest getUpdateRequestBody(String row, int sheetId) {
        if (TextUtils.isEmpty(row)) {
            row = "value1,value2,value3,value4";
        }
        String[] values = row.split(",");
        BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
        List<Request> requests = new ArrayList<>();

        Request request = new Request();
        AppendCellsRequest appendCellsRequest = new AppendCellsRequest();
        appendCellsRequest.setFields("*");
        appendCellsRequest.setSheetId(sheetId);

        List<RowData> rowDataList = new ArrayList<>();
        RowData rowData = new RowData();
        List<CellData> cellDataList = new ArrayList<>();
        CellData cellData;

        for (String value : values) {
            // Value
            cellData = new CellData();
            cellData.setUserEnteredValue(new ExtendedValue().setStringValue(value));
            cellDataList.add(cellData);
        }

        rowData.setValues(cellDataList);
        rowDataList.add(rowData);

        appendCellsRequest.setRows(rowDataList);
        request.setAppendCells(appendCellsRequest);
        requests.add(request);

        requestBody.setRequests(requests);
        return requestBody;
    }

    public static Spreadsheet getCreateRequest(
            @Nonnull String spreadsheetTitle,
            @Nonnull String entitiesSheetTitle,
            int entitiesSheetIndex,
            @Nonnull String templateSheetTitle,
            int templateSheetIndex,
            @Nonnull List<String> paymentMethods,
            @Nonnull List<String> categories) {
        Spreadsheet request = new Spreadsheet();

        SpreadsheetProperties properties = new SpreadsheetProperties();
        properties.setTitle(spreadsheetTitle)
                .setLocale(Locale.forLanguageTag(Locale.getDefault().toLanguageTag()).toString())
                .setAutoRecalc("ON_CHANGE")
                .setTimeZone(TimeZone.getDefault().getID())
                .setDefaultFormat(new CellFormat()
                        .setBackgroundColor(new Color()
                                .setBlue(1f)
                                .setGreen(1f)
                                .setRed(1f))
                        .setPadding(new Padding()
                                .setTop(2)
                                .setRight(3)
                                .setBottom(2)
                                .setLeft(3))
                        .setVerticalAlignment("BOTTOM")
                        .setWrapStrategy("OVERFLOW_CELL")
                        .setTextFormat(new TextFormat()
                                .setForegroundColor(new Color())
                                .setFontFamily("arial,sans,sans-serif")
                                .setFontSize(10)
                                .setBold(false)
                                .setItalic(false)
                                .setStrikethrough(false)
                                .setUnderline(false)));
        request.setProperties(properties);

        List<Sheet> sheets = new ArrayList<>();
        sheets.add(getEntitiesSheet(entitiesSheetTitle, entitiesSheetIndex,
                paymentMethods, categories));
        sheets.add(getExpenseSheet(templateSheetTitle, templateSheetIndex));

        request.setSheets(sheets);
        return request;
    }

    public static BatchUpdateSpreadsheetRequest getDuplicateSheetsRequest(
            int sourceSheetId,
            int startIndex,
            @Nonnull List<String> months) {
        BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
        List<Request> requestList = new ArrayList<>();

        for (String month : months) {
            Request request = new Request();

            DuplicateSheetRequest duplicateSheetRequest = new DuplicateSheetRequest()
                    .setSourceSheetId(sourceSheetId)
                    .setInsertSheetIndex(startIndex)
                    .setNewSheetName(month);

            request.setDuplicateSheet(duplicateSheetRequest);
            requestList.add(request);
            startIndex++;
        }
        requestBody.setRequests(requestList);
        return requestBody;
    }

    private static Sheet getExpenseSheet(String title, int index) {
        int rows = 300;
        int columns = 7;
        Sheet sheet = new Sheet();
        sheet.setProperties(getSheetProperties(title, index, rows, columns));

        List<GridData> dataList = new ArrayList<>();
        GridData data = new GridData();
        List<RowData> rowDataList = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            RowData rowData = new RowData();
            List<CellData> valuesList = new ArrayList<>();
            CellData value;

            // Date
            value = getEmptyCell();
            value.setDataValidation(new DataValidationRule()
                    .setCondition(new BooleanCondition()
                            .setType("DATE_IS_VALID")));
            valuesList.add(value);

            // Description
            value = getEmptyCell();
            valuesList.add(value);

            // Store
            value = getEmptyCell();
            valuesList.add(value);

            // Amount
            value = getEmptyCell();
            valuesList.add(value);

            // Payment Method
            value = getEmptyCell();
            List<ConditionValue> conditionValuesList = new ArrayList<>();
            conditionValuesList
                    .add(new ConditionValue().setUserEnteredValue("=Entities!$A$1:$A$20"));
            value.setDataValidation(new DataValidationRule()
                    .setCondition(new BooleanCondition()
                            .setType("ONE_OF_RANGE")
                            .setValues(conditionValuesList))
                    .setShowCustomUi(true));
            valuesList.add(value);

            // Category
            value = getEmptyCell();
            conditionValuesList = new ArrayList<>();
            conditionValuesList
                    .add(new ConditionValue().setUserEnteredValue("=Entities!$C$1:$C$20"));
            value.setDataValidation(new DataValidationRule()
                    .setCondition(new BooleanCondition()
                            .setType("ONE_OF_RANGE")
                            .setValues(conditionValuesList))
                    .setShowCustomUi(true));
            valuesList.add(value);

            // Flag
            value = getEmptyCell();
            conditionValuesList = new ArrayList<>();
            conditionValuesList
                    .add(new ConditionValue().setUserEnteredValue("FLAG"));
            value.setDataValidation(new DataValidationRule()
                    .setCondition(new BooleanCondition()
                            .setType("ONE_OF_LIST")
                            .setValues(conditionValuesList))
                    .setShowCustomUi(true));
            valuesList.add(value);

            rowData.setValues(valuesList);
            rowDataList.add(rowData);
        }
        data.setRowData(rowDataList);
        dataList.add(data);

        sheet.setData(dataList);

        return sheet;
    }

    private static Sheet getEntitiesSheet(@Nonnull String title,
            int index,
            @Nonnull List<String> paymentMethods,
            @Nonnull List<String> categories) {
        int rows = 22;
        int columns = 13;
        Sheet sheet = new Sheet();
        sheet.setProperties(getSheetProperties(title, index, rows, columns));

        List<GridData> dataList = new ArrayList<>();
        GridData data = new GridData();
        List<RowData> rowDataList = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < 22; rowIndex++) {
            RowData rowData = new RowData();
            List<CellData> valuesList = new ArrayList<>();
            CellData value;

            // Payment Method
            if (rowIndex < paymentMethods.size()) {
                value = getValueCell(paymentMethods.get(rowIndex));
            } else {
                value = getEmptyCell();
            }
            valuesList.add(value);

            // Empty cell
            value = getEmptyCell();
            valuesList.add(value);

            // Category
            if (rowIndex < categories.size()) {
                value = getValueCell(categories.get(rowIndex));
            } else {
                value = getEmptyCell();
            }
            valuesList.add(value);

            // Empty cell
            value = getEmptyCell();
            valuesList.add(value);

            rowData.setValues(valuesList);
            rowDataList.add(rowData);
        }
        data.setRowData(rowDataList);
        dataList.add(data);

        sheet.setData(dataList);
        return sheet;
    }

    private static SheetProperties getSheetProperties(@Nonnull String title, int index,
            int rows, int columns) {
        return new SheetProperties()
                .setTitle(title)
                .setIndex(index)
                .setSheetType("GRID")
                .setGridProperties(
                        new GridProperties()
                                .setRowCount(rows)
                                .setColumnCount(columns));
    }

    private static CellData getEmptyCell() {
        return new CellData()
                .setUserEnteredFormat(getUserEnteredFormat())
                .setEffectiveFormat(getEffectiveFormat());
    }

    private static CellData getValueCell(String value) {
        return new CellData()
                .setUserEnteredValue(new ExtendedValue().setStringValue(value))
                .setEffectiveValue(new ExtendedValue().setStringValue(value))
                .setFormattedValue(value)
                .setUserEnteredFormat(getUserEnteredFormat())
                .setEffectiveFormat(getEffectiveFormat());
    }

    private static CellFormat getUserEnteredFormat() {
        return new CellFormat()
                .setTextFormat(new TextFormat().setFontFamily("Roboto"));
    }

    private static CellFormat getEffectiveFormat() {
        return new CellFormat()
                .setTextFormat(new TextFormat().setFontFamily("Roboto").setFontSize(10));
    }
}
