package de.thm.ap.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.ContentProvider;

public class RecordContentProvider extends ContentProvider {

    static final String PROVIER_NAME = "de.thm.ap.persistence.ContactProvider";

    @Override
    public boolean onCreate() {
        return false;
    }

    @androidx.annotation.Nullable
    @Override
    public Cursor query(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String[] strings, @androidx.annotation.Nullable String s, @androidx.annotation.Nullable String[] strings1, @androidx.annotation.Nullable String s1) {
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public String getType(@androidx.annotation.NonNull Uri uri) {
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public Uri insert(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String s, @androidx.annotation.Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues contentValues, @androidx.annotation.Nullable String s, @androidx.annotation.Nullable String[] strings) {
        return 0;
    }
}