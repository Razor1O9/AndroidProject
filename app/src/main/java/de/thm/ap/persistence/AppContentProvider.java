package de.thm.ap.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteQueryBuilder;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class AppContentProvider extends ContentProvider {

    static final String PROVIER_NAME = "de.thm.ap.persistence.ContactProvider";
    // ID für diesen Content Provider
    public static String AUTHORITY = "de.thm.ap.records.cp";
    private static final String RECORD_PATH = "records";
    private static UriMatcher URI_MATCHER;
    private static final int RECORDS = 1;
    private static final int RECORD_ID = 2;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        // content://de.thm.ap.records.cp/records
        URI_MATCHER.addURI(AUTHORITY, RECORD_PATH, RECORDS);
        // content://de.thm.ap.records.cp/records/# (# Nummernplatzhalter)
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
}