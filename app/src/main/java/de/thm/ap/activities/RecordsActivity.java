package de.thm.ap.activities;

import android.content.DialogInterface;
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
import android.widget.AbsListView;
import android.widget.Toast;
import android.support.v7.view.ActionMode;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import android.view.MenuItem;

import de.thm.ap.persistence.RecordDAO;

/*
 / This class is the main class and displays all current records in a table
 */

public class RecordsActivity extends AppCompatActivity {
    private ActionMode mActionMode;
    private ListView recordsListView;
    private List<Record> records = new ArrayList<>();
    private List<Record> selectedRecords = new ArrayList<>();
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
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selectedRecordCounter++;
                    mode.setTitle(selectedRecordCounter + " ausgewählt");
                    selectedRecords.add(records.get(position));
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
                        new RecordDAO(getBaseContext()).delete(selectedRecords);
                        Toast.makeText(getBaseContext(), selectedRecordCounter + " Elemente gelöscht",Toast.LENGTH_SHORT).show();
                        selectedRecordCounter = 0;
                        mode.finish(); // Action picked, so close the CAB
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
                            mailContent = mailContent + r.getModuleName() + " " + r.getModuleNum() + " " + "(" + r.getMark() + "%" + r.getCrp() + "crp)\n";
                        }
                        emailIntent.putExtra(Intent.EXTRA_TEXT, mailContent);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Email senden"));
                            finish();
                            Log.i("Senden abgeschlossen", "");
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(RecordsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getBaseContext(), selectedRecordCounter + " Elemente gesendet",Toast.LENGTH_SHORT).show();
                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                mActionMode = null;
            }
        });
    }

//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//        //  Called when the action mode is created; startActionMode() was called
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            mode.getMenuInflater().inflate(R.menu.context_menu, menu);
//            mode.setTitle("Choose your option");
////          Inflate a menu resource providing context_menu menu items
////          MenuInflater inflater = mode.getMenuInflater();
////          inflater.inflate(R.menu.context_menu, menu);
//            return true;
//        }
//
//        // Called each time the action mode is shown. Always called after onCreateActionMode, but
//        // may be called multiple times if the mode is invalidated.
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false; // Return false if nothing is done
//        }
//        @Override
//        // Called when the user selects a contextual menu item
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            // Respond to clicks on the actions in the CAB
//            switch (item.getItemId()) {
//                case R.id.menu_delete:
//                    new RecordDAO(getBaseContext()).delete(selectedRecords);
//                    Toast.makeText(getBaseContext(), selectedRecordCounter + " Elemente gelöscht",Toast.LENGTH_SHORT).show();
//                    selectedRecordCounter = 0;
//                    mode.finish(); // Action picked, so close the CAB
//                    return true;
//                case R.id.menu_send:
//                    Log.i("Email senden", "");
//                    String[] TO = {""};
//                    String[] CC = {""};
//                    String mailContent = "";
//                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//                    emailIntent.setData(Uri.parse("mailto:"));
//                    emailIntent.setType("text/plain");
//                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Meine Leistungen " + selectedRecordCounter);
//                    for (Record r : selectedRecords) {
//                        mailContent = mailContent + r.getModuleName() + " " + r.getModuleNum() + " " + "(" + r.getMark() + "%" + r.getCrp() + "crp)\n";
//                    }
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, mailContent);
//
//                    try {
//                        startActivity(Intent.createChooser(emailIntent, "Email senden"));
//                        finish();
//                        Log.i("Senden abgeschlossen", "");
//                    } catch (android.content.ActivityNotFoundException ex) {
//                        Toast.makeText(RecordsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(getBaseContext(), selectedRecordCounter + " Elemente gesendet",Toast.LENGTH_SHORT).show();
//                    mode.finish(); // Action picked, so close the CAB
//                    return true;
//                default:
//                    return false;
//            }
//        }
//
//        // Called when the user exits the action mode
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();
        records = new RecordDAO(this).findAll();
        ArrayAdapter<Record> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, records);
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
//        recordsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mActionMode != null) {
//                    return false;
//                }
//
//                mActionMode = startSupportActionMode(mActionModeCallback);
//                view.setSelected(true);
//                return true;
//            }
//        });
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
                AlertDialog.Builder stats = new AlertDialog.Builder(this);
                AlertDialog statDialog = stats.create();
                statDialog.setTitle(getString(R.string.statistics_header));
                statDialog.setMessage(
                        getString(R.string.statistics_record_count) + " " + records.size() + "\n" +
                                getString(R.string.statistics_halfweight_record_count) + " " + statistics.getSumHalfWeighted() + "\n" +
                                getString(R.string.statistics_sum) + " " + statistics.getSumCrp() + "\n" +
                                getString(R.string.statistics_crp_left) + " " + statistics.getCrpToEnd() + "\n" +
                                getString(R.string.statistics_average) + " " + statistics.getAverageMark() + "\n"

                );
                stats.setNegativeButton(R.string.statistics_close_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();

                    }
                });
                statDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
