package de.thm.ap.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import de.thm.ap.R;
import de.thm.ap.records.model.Record;
import android.view.Menu;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.List;
import de.thm.ap.persistence.RecordDAO;
import android.view.MenuItem;
import android.content.Intent;

/*
This class is the main class and displays all current records in a table
 */

public class RecordsActivity extends AppCompatActivity {
    private ListView recordsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recordsListView = findViewById(R.id.records_list);
        recordsListView.setEmptyView(findViewById(R.id.records_list_empty));
    }
    @Override
    protected void onStart() {
        super.onStart();
        List<Record> records= new RecordDAO(this).findAll();
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        recordsListView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        // This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.records, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(this, RecordFormActivity.class);
                startActivity(i);
                return true;
            case R.id.action_stats:
                AlertDialog.Builder stats = new AlertDialog.Builder(this);// ToDo AlertDialog anzeigen (Statistik.class Daten holen)
                // neues stat Object anlegen und toString override
                // stats.setMessage(stats.toString); // Inhalt des AltertDialog
                // display stats
        }
        return super.onOptionsItemSelected(item);
    }
}
