package de.thm.ap.activities;

import android.content.Intent;
import android.os.Bundle;

import de.thm.ap.R;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.view.View;

import de.thm.ap.persistence.AppDatabase;
import de.thm.ap.records.model.Module;
import de.thm.ap.records.model.Record;

import java.util.List;
import java.util.Optional;

import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Switch;

/*
 / This class contains to formula to enter a new record
 */

public class RecordFormActivity extends AppCompatActivity {
    private Switch term, halfweight;
    private EditText moduleNum, creditPoints, mark;
    private AutoCompleteTextView moduleName;
    private Spinner year;
    private Boolean isUpdate = false;
    private int oldID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_form);

        // Show up button in action bar
        Optional.ofNullable(getSupportActionBar()).ifPresent(actionBar -> actionBar.setDisplayHomeAsUpEnabled(true));

        // get views
        moduleNum = findViewById(R.id.module_num);
        moduleName = findViewById(R.id.module_name);
        year = findViewById(R.id.year_value);
        creditPoints = findViewById(R.id.creditpoints);
        mark = findViewById(R.id.mark);
        term = findViewById(R.id.term_value);
        halfweight = findViewById(R.id.halfweight_value);

        // configure suggestions in auto complete text view
        String[] names = getResources().getStringArray(R.array.module_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        moduleName.setAdapter(adapter);

        // configure year spinner
        String[] semesterYear_list = new String[] {
                "2009","2010","2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018"
        };
        ArrayAdapter<String> semesterYear_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, semesterYear_list);
        semesterYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        year.setAdapter(semesterYear_adapter);
        year.setSelection(9);

        // Do update stuff
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Record oldRecord = (Record)extras.getSerializable("oldRecord");
            oldID = oldRecord.getId();
            isUpdate = true;
            moduleName.setText(oldRecord.getModuleName());
            moduleNum.setText(oldRecord.getModuleNum());
            mark.setText(String.valueOf(oldRecord.getMark()));
            creditPoints.setText(String.valueOf(oldRecord.getCrp()));
            term.setChecked(oldRecord.isSummerTerm());
            halfweight.setChecked(oldRecord.getHalfWeight());
            for (int i=0;i<year.getCount();i++){
                if (year.getItemAtPosition(i).toString().equalsIgnoreCase(String.valueOf(oldRecord.getYear()))){
                    year.setSelection(i);
                }
            }
        }
    }

    public void onSave(View view) {
        String selectedName = moduleName.getText().toString();
        String selectedNum = moduleNum.getText().toString();
        String selectedYear = year.getSelectedItem().toString();
        String selectedCrp = creditPoints.getText().toString();
        String selectedMark = mark.getText().toString();
        Boolean isValid = true;

        if (!selectedNum.isEmpty() && !isModuleNr(selectedNum)) {
            moduleNum.setError(getString(R.string.module_number_not_valid));
            isValid = false;
        }
        if (selectedName.isEmpty()) {
            moduleName.setError(getString(R.string.module_name_not_empty));
            isValid = false;
        }

        if (selectedMark.isEmpty()) {
            mark.setError(getString(R.string.mark_not_empty));
            isValid = false;
        }

        if (!selectedCrp.equals("3") &&
                !selectedCrp.equals("6") &&
                !selectedCrp.equals("9") &&
                !selectedCrp.equals("15")) {
            creditPoints.setError(getString(R.string.credit_points_not_valid));
            isValid = false;
        }
        if(!isNumeric(selectedMark)){
            if(!isNull(selectedMark)){
                isValid = false;
            }
        }
        else if ((Integer.parseInt(String.valueOf(selectedMark)) < 0) | (Integer.parseInt(String.valueOf(selectedMark)) > 100)) {
            mark.setError(getString(R.string.mark_not_valid));
            isValid = false;
        }
        if (isValid) {
            Record record = new Record("", "", 0, false, false, 0, 0);
            record.setModuleNum(selectedNum);
            record.setModuleName(selectedName);
            record.setYear(Integer.parseInt(selectedYear));
            record.setHalfWeighted(halfweight.isChecked());
            record.setSummerTerm(term.isChecked());
            record.setCrp(Integer.parseInt(String.valueOf(selectedCrp)));
            record.setID(oldID);
            if(isParsable(selectedMark)){
                record.setMark(Integer.parseInt(selectedMark));
            } else {
                record.setMark(0);
            }
            // persist record and finish activity
            if (isUpdate) {
//                new RecordDAO(this).update(record);
                AppDatabase.getDb(this).recordDAO().update(record);
                Intent returnIntent = new Intent();
                setResult(RecordsActivity.RESULT_OK, returnIntent);
                finish();
            } else {
//                new RecordDAO(this).persist(record);
                AppDatabase.getDb(this).recordDAO().persist(record);
                Intent returnIntent = new Intent();
                setResult(RecordsActivity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_module_pick:

                List<Module> modules = AppDatabase.getModuleDb(this).moduleDAO().findAllModules();
                String[] moduleNames = new String[modules.size()];
                for (int idx = 0; idx < modules.size(); ++idx)
                    moduleNames[idx] = modules.get(idx).toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(RecordFormActivity.this);
                builder.setTitle(R.string.module_picker).setItems(moduleNames, (dialog, which) -> {
                    Module module = modules.get(which);
                    moduleNum.setText(module.getNr());
                    moduleName.setText(module.getName());

                    creditPoints.setText(module.getCrp());


                });
                builder.create();
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static boolean isNull(String s){
        return s.matches("[Nn][Uu][Ll][Ll]");
    }

    private static boolean isNumeric(String s){
        return s.matches("\\d+");
    }

    private static boolean isParsable(String s){
        boolean parsable = true;
        try{
            Integer.parseInt(s);
        } catch (Exception e){
            parsable = false;
        }
        return parsable;
    }

    private static boolean isModuleNr(String s){
        return s.matches("[a-zA-Z]{2}[0-9]{4}");
    }
}

