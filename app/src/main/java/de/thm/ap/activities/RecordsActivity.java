package de.thm.ap.activities;


import android.content.ContentProvider;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.thm.ap.R;
import de.thm.ap.logic.Stats;
import de.thm.ap.persistence.AppContentProvider;
import de.thm.ap.persistence.AppDatabase;
import de.thm.ap.records.model.Record;

import android.content.Intent;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;


/*
 / This class is the main class and displays all current records in a table
 */

public class RecordsActivity extends AppCompatActivity {
    private static final String TAG = RecordsActivity.class.getName();
    private ListView recordsListView;
    private List<Record> records = new ArrayList<>();
    private List<Record> selectedRecords = new ArrayList<>();
    private int selectedRecordCounter = 0;
    ArrayAdapter<Record> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recordsListView = findViewById(R.id.records_list);
        recordsListView.setEmptyView(findViewById(R.id.records_list_empty));
        recordsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        recordsListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selectedRecordCounter++;
                    mode.setTitle(selectedRecordCounter + " ausgewählt");
                    selectedRecords.add(records.get(position));
                    //mode.getCustomView().isSelected();
                } else {
                    selectedRecordCounter--;
                    mode.setTitle(selectedRecordCounter + " ausgewählt");
                    selectedRecords.remove(records.get(position));
                }
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.context_menu, menu);
                mode.setTitle("Choose your option");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.menu_delete:
//                        new RecordDAO(getBaseContext()).delete(selectedRecords);
                        AppDatabase.getDb(RecordsActivity.this).recordDAO().delete(selectedRecords);
                        Toast.makeText(getBaseContext(), selectedRecordCounter + " Elemente gelöscht", Toast.LENGTH_SHORT).show();
                        selectedRecordCounter = 0;
                        mode.finish(); // Action picked, so close the CAB
                        adapter.clear();
//                        records = new RecordDAO(getBaseContext()).findAll();
                        records = AppDatabase.getDb(RecordsActivity.this).recordDAO().findAll();
                        adapter.addAll(records);
                        return true;
                    case R.id.menu_send:
                        Log.i("Email senden", "");
                        String[] TO = {""};
                        String[] CC = {""};
                        String mailContent = "";
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);

                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Meine Leistungen " + selectedRecordCounter);
                        for (Record r : selectedRecords) {
                            mailContent = mailContent + r.getModuleName() + " " + r.getModuleNum() + " " + "(" + r.getMark() + "% " + r.getCrp() + " crp)\n";
                        }
                        emailIntent.putExtra(Intent.EXTRA_TEXT, mailContent);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Email senden"));
                            finish();
                            Log.i("Senden abgeschlossen", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(RecordsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getBaseContext(), selectedRecordCounter + " Elemente gesendet", Toast.LENGTH_SHORT).show();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        records = AppDatabase.getDb(RecordsActivity.this).recordDAO().findAll();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, records);
        recordsListView.setAdapter(adapter);
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String action = getIntent().getAction();
                if (Intent.ACTION_PICK.equals(action)) { // On click -> Get the selected item
                    Record selectedRecord = (Record) parent.getItemAtPosition(position);
                    Intent returnIntent = new Intent();
                    returnIntent.setData(Uri.parse(String.valueOf(selectedRecord)));
                    setResult(RecordsActivity.RESULT_OK, returnIntent);
                    finish();
                } else { // On click -> edit List Item
                    Record selectedRecord = (Record) parent.getItemAtPosition(position);
                    Intent startThis = new Intent(view.getContext(), RecordFormActivity.class);
                    startThis.putExtra("oldRecord", selectedRecord);
                    startActivityForResult(startThis, 1);
                    view.setSelected(true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RecordFormActivity.RESULT_OK) {
                adapter.clear();
//                records = new RecordDAO(this).findAll();
                records = AppDatabase.getDb(RecordsActivity.this).recordDAO().findAll();
                adapter.addAll(records);
            }
            if (resultCode == RecordFormActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
                Intent intent = new Intent(getApplicationContext(), RecordFormActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_stats:
                //noinspection unchecked
                new StatsTask(RecordsActivity.this).execute(records);


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Uses a List<Record> for calculation
     * Returns the Stats of this list (Return Value for doInBackground)
     */

    private class StatsTask extends AsyncTask<List<Record>, Void, Stats> {

        private Context context;
        private ProgressBar progressBar;

        public StatsTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // start progress bar
            progressBar = findViewById(R.id.indeterminateBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        /**
         * Creates an object of Stats.class
         * Stats.class calculates all stats.
         * @param recordList
         * @return stats Object
         */
        @Override
        protected Stats doInBackground(List<Record>... recordList) {
            Stats stats = new Stats(recordList[0]);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return stats;
        }

        @Override
        protected void onPostExecute(Stats stats) {
            // stop progress bar
            // show result dialog
            AlertDialog.Builder statBuilder = new AlertDialog.Builder(context);
            //AlertDialog statDialog = statBuilder.create();
            statBuilder.setTitle(getString(R.string.statistics_header));
            statBuilder.setMessage(
                    context.getString(R.string.statistics_record_count) + " " + records.size() + "\n" +
                            context.getString(R.string.statistics_halfweight_record_count) + " " + stats.getSumHalfWeighted() + "\n" +
                            context.getString(R.string.statistics_sum) + " " + stats.getSumCrp() + "\n" +
                            context.getString(R.string.statistics_crp_left) + " " + stats.getCrpToEnd() + "\n" +
                            context.getString(R.string.statistics_average) + " " + stats.getAverageMark() + "\n"

            );
            statBuilder.setNeutralButton(R.string.statistics_close_button, null);
            statBuilder.show();
            progressBar.setVisibility(View.GONE);
        }

    }
}


