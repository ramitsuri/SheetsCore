package com.ramitsuri.sheetscoresample;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ramitsuri.sheetscore.DriveProcessor;
import com.ramitsuri.sheetscore.googleSignIn.AccountManager;
import com.ramitsuri.sheetscore.googleSignIn.SignInResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.ramitsuri.sheetscoresample.Constants.SCOPES;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    public static final int GOOGLE_SIGN_IN = 100;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 101;

    private static final String SETTINGS_KEY_ACCOUNT_NAME = "settings_key_account_name";
    private static final String SETTINGS_KEY_ACCOUNT_TYPE = "settings_key_account_type";

    Button mBtnSignIn, mBtnCopy, mBtnInsert, mBtnGetRange, mBtnGetRanges, mBtnGetSheets, mBtnCreate,
            mBtnDuplicateSheets;
    EditText mEditPrimaryId, mEditCopyId, mEditRange, mEditRowValues, mEditSheetId,
            mEditSourceSheetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditPrimaryId = findViewById(R.id.edit_primary_id);
        mEditCopyId = findViewById(R.id.edit_source_file_id);
        mEditRange = findViewById(R.id.edit_range);
        mEditRowValues = findViewById(R.id.edit_insert_text);
        mEditSheetId = findViewById(R.id.edit_sheet_id);
        mEditSourceSheetId = findViewById(R.id.edit_source_sheet_id);

        mBtnSignIn = findViewById(R.id.btn_sign_in);
        mBtnSignIn.setOnClickListener(this);

        mBtnCopy = findViewById(R.id.btn_copy);
        mBtnCopy.setOnClickListener(this);

        mBtnInsert = findViewById(R.id.btn_insert);
        mBtnInsert.setOnClickListener(this);

        mBtnGetRange = findViewById(R.id.btn_get_range);
        mBtnGetRange.setOnClickListener(this);

        mBtnGetRanges = findViewById(R.id.btn_get_ranges);
        mBtnGetRanges.setOnClickListener(this);

        mBtnGetSheets = findViewById(R.id.btn_get_sheets);
        mBtnGetSheets.setOnClickListener(this);

        mBtnCreate = findViewById(R.id.btn_create);
        mBtnCreate.setOnClickListener(this);

        mBtnDuplicateSheets = findViewById(R.id.btn_duplicate_sheets);
        mBtnDuplicateSheets.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Account account = null;
        String accountName = getAccountName();
        String accountType = getAccountType();
        if (accountName != null && accountType != null) {
            account = new Account(accountName, accountType);
        } else {
            Log.w(TAG, "onClick AccountType or Name null. Name " + accountName + ", Type " +
                    accountType);
            signIn();
            return;
        }

        if (v == mBtnSignIn) {
            signIn();
        } else if (v == mBtnCopy) { // COPY
            if (TextUtils.isEmpty(mEditCopyId.getText().toString().trim())) {
                Log.w(TAG, "Cannot make a copy. File id  empty");
                return;
            }
            SheetOperationHelper.copy(account, mEditCopyId.getText().toString().trim(), "New File");
        } else if (v == mBtnInsert) { // INSERT
            if (TextUtils.isEmpty(mEditPrimaryId.getText().toString().trim()) ||
                    TextUtils.isEmpty(mEditRowValues.getText().toString().trim()) ||
                    TextUtils.isEmpty(mEditSheetId.getText().toString().trim())) {
                Log.w(TAG, "Cannot get sheets. Spreadsheet id or row values or sheet id empty");
                return;
            }
            int sheetId;
            try {
                String value = mEditSheetId.getText().toString().trim();
                sheetId = Integer.parseInt(value);
            } catch (Exception e) {
                Log.w(TAG, "Cannot get sheets. sheet id not parsed");
                return;
            }
            SheetOperationHelper.insertRange(account, mEditPrimaryId.getText().toString().trim(),
                    mEditRowValues.getText().toString().trim(), sheetId);
        } else if (v == mBtnGetRange) { // GET RANGE
            if (TextUtils.isEmpty(mEditPrimaryId.getText().toString().trim()) ||
                    TextUtils.isEmpty(mEditRange.getText().toString().trim())) {
                Log.w(TAG, "Cannot get range. Spreadsheet  id or range empty");
                return;
            }
            SheetOperationHelper.getRange(account, mEditPrimaryId.getText().toString().trim(),
                    mEditRange.getText().toString().trim());
        } else if (v == mBtnGetRanges) { // GET RANGE
            if (TextUtils.isEmpty(mEditPrimaryId.getText().toString().trim())) {
                Log.w(TAG, "Cannot get ranges. Spreadsheet  id empty");
                return;
            }
            List<String> ranges = new ArrayList<>();
            ranges.add("Jan!A:G");
            ranges.add("Feb!A:G");
            ranges.add("Mar!A:G");
            SheetOperationHelper.getRanges(account, mEditPrimaryId.getText().toString().trim(),
                    ranges);
        } else if (v == mBtnGetSheets) { // GET SHEETS
            if (TextUtils.isEmpty(mEditPrimaryId.getText().toString().trim())) {
                Log.w(TAG, "Cannot get sheets. Spreadsheet  id  empty");
                return;
            }
            SheetOperationHelper.getSheets(account, mEditPrimaryId.getText().toString().trim());
        } else if (v == mBtnCreate) { // Create new spreadsheet
            String spreadsheetTitle = "Test Spread";
            String entitiesTitle = "Entities";
            int entitiesSheetIndex = 0;
            String templateTitle = "Template";
            int templateSheetIndex = 1;

            SheetOperationHelper
                    .createSpreadsheet(account, spreadsheetTitle, entitiesTitle, entitiesSheetIndex,
                            templateTitle, templateSheetIndex, getPaymentMethods(), getCategories(),
                            getMonths());
        } else if (v == mBtnDuplicateSheets) { // Duplicate Sheets
            if (TextUtils.isEmpty(mEditPrimaryId.getText().toString().trim()) ||
                    TextUtils.isEmpty(mEditSourceSheetId.getText().toString().trim())) {
                Log.w(TAG, "Cannot duplicate sheets. Spreadsheet id or source sheet id empty");
                return;
            }
            int sheetId;
            try {
                String value = mEditSourceSheetId.getText().toString().trim();
                sheetId = Integer.parseInt(value);
            } catch (Exception e) {
                Log.w(TAG, "Cannot duplicate sheets. sheet id not parsed");
                return;
            }
            SheetOperationHelper
                    .duplicateSheets(account, mEditPrimaryId.getText().toString().trim(), sheetId,
                            2, getMonths());
        }
    }

    private void signIn() {
        AccountManager accountManager = new AccountManager();
        SignInResponse response = accountManager.prepareSignIn(this, SCOPES);
        if (response.getGoogleSignInAccount() != null) {
            Account account = response.getGoogleSignInAccount().getAccount();
            if (account != null) {
                saveAccountDetailsIfNecessary(account);
                Log.d(TAG, "Signed in");
            } else {
                Log.w(TAG, "Account is null");
            }
        } else if (response.getGoogleSignInIntent() != null) {
            // request account access
            startActivityForResult(response.getGoogleSignInIntent(), GOOGLE_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GOOGLE_SIGN_IN) {
            Account account = AccountManager.getSignInAccountFromIntent(data);
            if (account != null) {
                saveAccountDetailsIfNecessary(account);
            } else {
                Log.i(TAG, "Sign-in failed.");
            }
        } else if (requestCode == REQUEST_CODE_OPEN_DOCUMENT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    //SheetOperationHelper.openFileFromFilePicker(uri,
                    //      new Account(getAccountName(), getAccountType()));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Opens the Storage Access Framework file picker using {@link #REQUEST_CODE_OPEN_DOCUMENT}.
     */
    private void openFilePicker(@Nonnull final Account account) {
        DriveProcessor processor =
                new DriveProcessor(MainApplication.getInstance(),
                        MainApplication.getInstance().getApplicationInfo().name,
                        account,
                        Arrays.asList(SCOPES));
        Log.d(TAG, "Opening file picker.");

        Intent pickerIntent = processor.createFilePickerIntent();

        // The result of the SAF Intent is handled in onActivityResult.
        startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT);
    }

    private void saveAccountDetailsIfNecessary(Account account) {
        String accountName = getAccountName();
        String accountType = getAccountType();
        if (accountName == null && accountType == null) {
            setAccountName(account.name);
            setAccountType(account.type);
        }
    }

    public static String getAccountName() {
        return PrefHelper.get(SETTINGS_KEY_ACCOUNT_NAME, null);
    }

    public static void setAccountName(String accountName) {
        PrefHelper.set(SETTINGS_KEY_ACCOUNT_NAME, accountName);
    }

    public static String getAccountType() {
        return PrefHelper.get(SETTINGS_KEY_ACCOUNT_TYPE, null);
    }

    public static void setAccountType(String accountType) {
        PrefHelper.set(SETTINGS_KEY_ACCOUNT_TYPE, accountType);
    }

    private static List<String> getMonths() {
        List<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");
        return months;
    }

    private static List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("BBVA CH");
        categories.add("Discover");
        categories.add("Cash");
        categories.add("Fidelity");
        categories.add("Amazon");
        categories.add("Master 53");
        categories.add("AMEX");
        categories.add("Splitwise");
        categories.add("Citi");
        categories.add("Gift");
        return categories;
    }

    private static List<String> getPaymentMethods() {
        List<String> payments = new ArrayList<>();
        payments.add("Travel");
        payments.add("Entertainment");
        payments.add("Utilities");
        payments.add("Rent");
        payments.add("Home");
        payments.add("Food");
        payments.add("Groceries");
        payments.add("Tech");
        payments.add("Miscellaneous");
        payments.add("Fun");
        payments.add("Personal");
        payments.add("Shopping");
        payments.add("Car");
        payments.add("Not Budgeted");
        return payments;
    }
}
