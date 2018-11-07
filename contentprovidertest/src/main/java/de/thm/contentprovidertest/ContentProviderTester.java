package de.thm.contentprovidertest;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ContentProviderTester extends AppCompatActivity {
    private static final String TAG = ContentProviderTester.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_tester);
        // Get data from content provider
        Uri uri = Uri.parse("content://de.thm.ap.records.cp/records");
        ContentResolver cr = getContentResolver();
        String[] projection = {"id", "moduleName"};
        Cursor c = cr.query(uri, projection, null, null, "moduleName");
        if (c != null) {
            while (c.moveToNext()) {
                Log.i(TAG, "id: " + c.getLong(0) + " module name: " + c.getString(1));
            }
        }
    }
}
