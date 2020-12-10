package com.ramitsuri.sheetscoresample;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;

public class Constants {
    public static final String[] SCOPES_FULL = {SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE};
    public static final String[] SCOPES_LIMITED = {DriveScopes.DRIVE_FILE};
    public static final String[] SCOPES = SCOPES_FULL;
}
