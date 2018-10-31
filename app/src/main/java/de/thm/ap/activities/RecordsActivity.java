package de.thm.ap.activities;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import de.thm.ap.R;
import de.thm.ap.logic.Stats;
import de.thm.ap.records.model.Record;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.List;

import de.thm.ap.persistence.RecordDAO;

import android.view.MenuItem;


/*
 / This class is the main class and displays all current records in a table
 */

public class RecordsActivity extends AppCompatActivity {
    private ListView recordsListView;
    private List<Record> records;
    private List<Record> selectedRecords;
    private int selectedRecordCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordsListView = findViewById(R.id.records_list);
        recordsListView.setEmptyView(findViewById(R.id.records_list_empty));

        recordsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        recordsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selectedRecords.add(records.get(position));
                    selectedRecordCounter++;
                    mode.setTitle(selectedRecordCounter + " ausgewählt");
                } else {
                    selectedRecords.remove(records.get(position));
                    selectedRecordCounter--;
                    mode.setTitle(selectedRecordCounter + " ausgewählt");
                }
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        new RecordDAO(getBaseContext()).delete(selectedRecords);
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    case R.id.menu_send:
                        Log.i("Email senden","");
                        String[] TO = {""};
                        String[] CC = {""};
                        String mailContent = "";
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);

                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Meine Leistungen " + selectedRecordCounter);
                        for (Record r: selectedRecords) {
                            mailContent = mailContent + r.getModuleName() + " " + r.getModuleNum() + " " + "(" + r.getMark() +"%" + r.getCrp() + "crp)\n";
                        }
                        emailIntent.putExtra(Intent.EXTRA_TEXT, mailContent);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Email senden"));
                            finish();
                            Log.i("Senden abgeschlossen", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(RecordsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }

                        mode.finish(); // Action picked, so close the CAB
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        records = new RecordDAO(this).findAll();
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        recordsListView.setAdapter(adapter);

        // On click -> edit List Item
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                Record selectedRecord = (Record) parent.getItemAtPosition(position);
                Intent startThis = new Intent(view.getContext(), RecordFormActivity.class);
                startThis.putExtra("oldRecord", selectedRecord);
                startActivity(startThis);
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
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getApplicationContext(),RecordFormActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_stats:
                AlertDialog.Builder stats = new AlertDialog.Builder(this);
                stats.setTitle(getString(R.string.statistics_header));
                stats.setMessage(
                        getString(R.string.statistics_record_count) + " " + records.size() + "\n" +
                                getString(R.string.statistics_halfweight_record_count) + " " + statistics.getSumHalfWeighted() + "\n" +
                                getString(R.string.statistics_sum) + " " + statistics.getSumCrp() + "\n" +
                                getString(R.string.statistics_crp_left) + " " + statistics.getCrpToEnd() + "\n" +
                                getString(R.string.statistics_average) + " " + statistics.getAverageMark() + "\n"

                );
                stats.setNeutralButton("Schließen", null);
                stats.show();
        }
        return super.onOptionsItemSelected(item);
    }


    /*

     */
}
