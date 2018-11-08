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
import android.widget.Button;
import android.widget.TextView;

public class ContentProviderTester extends AppCompatActivity {
    private static final String TAG = ContentProviderTester.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_tester);
        final Button button = findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("list/record");
                startActivityForResult(intent, 1);
            }
        });


        // Get data from content provider
        Uri uri = Uri.parse("content://de.thm.ap/records");
        ContentResolver cr = getContentResolver();
        String[] projection = {"id", "module_name"};
        Cursor c = cr.query(uri, projection, null, null, "module_name");
        String allRecords = "";
        if (c != null) {
            while (c.moveToNext()) {
                allRecords = allRecords + "id: " + c.getLong(0) + " module name: " + c.getString(1) + "\n";
            }
        }
        Log.i(TAG, allRecords);
    }
    // Callback-Methode
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case RESULT_OK:
                    Uri recordData = data.getData();
                    TextView result = findViewById(R.id.record_name_id);
                    result.setText((CharSequence) recordData);
            case RESULT_CANCELED:
                return;
        }
    }
}
    // ToDo Create a Button (Action-Click) with startActivityForResult

    /*
    Intent
    Action Pick
    Data: list/record
     */
