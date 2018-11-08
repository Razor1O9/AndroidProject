package de.thm.ap.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteQueryBuilder;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Objects;
import de.thm.ap.records.model.Record;


public class AppContentProvider extends ContentProvider {
    // ID für diesen Content Provider
    public static String AUTHORITY = "de.thm.ap.records.cp";
    private static final String RECORD_PATH = "records";
    private static UriMatcher URI_MATCHER;
    private static final int RECORDS = 1;
    private static final int RECORD_ID = 2;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        // content://de.thm.ap.records/records
        URI_MATCHER.addURI(AUTHORITY, RECORD_PATH, RECORDS);
        // content://de.thm.ap.records/records/# (# Nummernplatzhalter)
        URI_MATCHER.addURI(AUTHORITY, RECORD_PATH + "/#", RECORD_ID);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SupportSQLiteQueryBuilder builder = SupportSQLiteQueryBuilder
                .builder("Record")
                .columns(projection)
                .orderBy(sortOrder);
        switch (URI_MATCHER.match(uri)) {
            case RECORDS:
                break;
            case RECORD_ID:
                builder.selection("id = ?", new Object[]{uri.getLastPathSegment()});
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        SupportSQLiteDatabase db = AppDatabase.getDb(getContext())
                .getOpenHelper()
                .getReadableDatabase();
        return db.query(builder.create());
    }


    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case RECORDS:
                return "vnd.android.cursor.dir/vnd.thm.ap.record";
            case RECORD_ID:
                return "vnd.android.cursor.item/vnd.thm.ap.record";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        switch (mode){
            case "r":
                try {
                    return ParcelFileDescriptor.open(writeCSV(getContext()), ParcelFileDescriptor.MODE_READ_ONLY);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            default:
                throw new FileNotFoundException("");
        }
    }
    public File writeCSV(Context context) throws IOException {
        String path = "records.csv";
        String outputCSV = ""; // Default CSV Text
        int counter = 0; // Column number
        OutputStreamWriter outputWriter = new OutputStreamWriter(Objects.requireNonNull(getContext()).openFileOutput(path, Context.MODE_PRIVATE));

        // Read Data
        List<Record> recordSamples = AppDatabase.getDb(getContext()).recordDAO().findAll();
        for (Record r : recordSamples) {
            counter++;
            // writes Data into the Text
            outputCSV = outputCSV + counter + ";" + r.getModuleNum() + ";" + r.getModuleName() + r.getYear() + r.isSummerTerm() + r.getHalfWeight() + r.getCrp() + r.getMark() + "\n";
        }
        try {
            outputWriter.write(outputCSV);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Activity", "created: " + outputCSV);
        return getContext().getFileStreamPath(path);
    }
}