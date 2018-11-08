package de.thm.contentprovidertest;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ContentProviderTester extends AppCompatActivity {
    private static final String TAG = ContentProviderTester.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_tester);
        // Get data from content provider
        Uri uri = Uri.parse("content://de.thm.ap/records");
        ContentResolver cr = getContentResolver();
        String[] projection = {"id", "module_name"};
        Cursor c = cr.query(uri, projection, null, null, "module_name");
        if (c != null) {
            while (c.moveToNext()) {
                Log.i(TAG, "id: " + c.getLong(0) + " module name: " + c.getString(1));
            }
        }
    }
    // ToDo Create a Button (Action-Click) with startActivityForResult

    /*
    Intent
    Action Pick
    Data: list/record
     */
//    void onButtonClick(View button) {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
//        startActivityForResult(intent, 1);
//
//        intent.setClassName(ContentProviderTester.this, de.thm.ap.activities.RecordsActivity.class.getName());
//        startActivityForResult(intent, PICK_BOOKMARK);
//
//        // Callback-Methode
//        void onActivityResult ( int requestCode, int resultCode, Intent data){
//            switch (requestCode) {
//                case 1:
//                    Uri contactData = data.getData();
//                    // read selected contact from URI
//            }
//        }
//    }
}