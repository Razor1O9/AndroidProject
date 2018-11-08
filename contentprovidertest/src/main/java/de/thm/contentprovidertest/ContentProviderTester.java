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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ContentProviderTester extends AppCompatActivity {
    private static final String TAG = ContentProviderTester.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_tester);
        // Get data from content provider
        Uri uri = Uri.parse("content://de.thm.ap.records.cp/records");
        ContentResolver cr = getContentResolver();
        String[] projection = {"id", "module_name", "module_number"};
        Cursor c = cr.query(uri, projection, null, null, "module_name");

        String allRecords = "";
        if (c != null) {
            while (c.moveToNext()) {
                allRecords = allRecords + "id: " + c.getLong(0) + " module name: " + c.getString(1) + " module number: " + c.getString(2) + "\n";
            }
        }
        TextView result = findViewById(R.id.record_display);
        result.setText(allRecords);
        TextView resultCSV = findViewById(R.id.resultCSV);
        result.setText(allRecords);

<<<<<<< Updated upstream
    public void onClickBtn(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("list/record");
        startActivityForResult(intent, 1); //TODO fix crash
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_content_provider_tester);
//        final Button button = findViewById(R.id.button_id);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Code here executes on main thread after user presses button
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("list/record");
//                startActivityForResult(intent, 1);
//            }
//        });
//
//
//        // Get data from content provider
//        Uri uri = Uri.parse("content://de.thm.ap/records");
//        ContentResolver cr = getContentResolver();
//        String[] projection = {"id", "module_name"};
//        Cursor c = cr.query(uri, projection, null, null, "module_name");
//        String allRecords = "";
//        if (c != null) {
//            while (c.moveToNext()) {
//                allRecords = allRecords + "id: " + c.getLong(0) + " module name: " + c.getString(1) + "\n";
//            }
//        }
//        TextView result = findViewById(R.id.record_name_id);
//        result.setText(allRecords);
//    }
//    // Callback-Methode
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        switch (requestCode) {
//            case RESULT_OK:
//                    Uri recordData = data.getData();
//                    TextView result = findViewById(R.id.record_name_id);
//                    result.setText((CharSequence) recordData);
//            case RESULT_CANCELED:
//                return;
//        }
//    }
=======
        Uri uriCSV = Uri.parse("content://de.thm.ap.raw.record");
        ContentResolver resolver = getContentResolver();
        try {
            StringBuilder content = new StringBuilder();
            InputStream is = resolver.openInputStream(uriCSV);
            is = resolver.openInputStream(uriCSV);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                content.append(line).append("\n");
                line = reader.readLine();
            }
            resultCSV.setText(content);
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
>>>>>>> Stashed changes
}

