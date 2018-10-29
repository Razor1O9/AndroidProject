package de.thm.ap.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.thm.ap.R;
import de.thm.ap.logic.Stats;
import de.thm.ap.records.model.Record;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.List;
import de.thm.ap.persistence.RecordDAO;
import android.view.MenuItem;
import android.content.Intent;

/*
 / This class is the main class and displays all current records in a table
 */

public class RecordsActivity extends AppCompatActivity {
    private ListView recordsListView;
    private List<Record> records;

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
        records = new RecordDAO(this).findAll();
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        recordsListView.setAdapter(adapter);

        // On click edit List Item
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                Record selectedRecord = (Record)parent.getItemAtPosition(position);

                //TODO: RecordFormActivity mit Kontructor(selectedRecord) aufrufen
                Intent i = new Intent(this, RecordFormActivity.class);

                i.putExtra("oldRecord", selectedRecord);

                startActivity(i);
            }
        });
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
        Stats statistics = new Stats(records);
        switch (item.getItemId()){
            case R.id.action_add:
            Intent i = new Intent(this, RecordFormActivity.class);
            startActivity(i);
            return true;
            case R.id.action_stats:
            AlertDialog.Builder stats = new AlertDialog.Builder(this);
            stats.setTitle(getString(R.string.statistics_header));
            stats.setMessage(
                        getString(R.string.statistics_record_count) + " " + records.size() + "\n" +
                        getString(R.string.statistics_halfweight_record_count) + " " + statistics.getSumHalfWeighted() + "\n" +
                        getString(R.string.statistics_sum) + " " +  statistics.getSumCrp() + "\n" +
                        getString(R.string.statistics_crp_left) + " " + statistics.getCrpToEnd() + "\n" +
                        getString(R.string.statistics_average) + " " + statistics.getAverageMark() + "\n"

                );
            stats.setNeutralButton("Schließen", null);
            stats.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
