package de.thm.ap.activities;

import android.os.Bundle;

import de.thm.ap.R;

import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.view.View;

import de.thm.ap.records.model.Record;
import de.thm.ap.persistence.RecordDAO;

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
        year.setSelection(4);

        // Do update stuff
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Record oldRecord = (Record)extras.getSerializable("oldRecord");
            isUpdate = true;
            moduleName.setText(oldRecord.getModuleName());
            moduleNum.setText(oldRecord.getModuleNum());
            mark.setText(String.valueOf(oldRecord.getMark()));
            creditPoints.setText(String.valueOf(oldRecord.getCrp()));
            term.setChecked(oldRecord.isSummerTerm());
            halfweight.setChecked(oldRecord.isHalfWeighted());
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
        Integer selectedCrp = Integer.valueOf(creditPoints.getText().toString());
        String selectedMark = mark.getText().toString();

        Boolean isValid = true;

        if (!("".equals(selectedNum)) && !isModuleNr(selectedNum)) {
            moduleNum.setError(getString(R.string.module_number_not_valid));
            isValid = false;
        }
        if ("".equals(selectedName)) {
            moduleName.setError(getString(R.string.module_name_not_empty));
            isValid = false;
        }
        if ("".equals(selectedCrp)) {
            creditPoints.setError(getString(R.string.credit_points_not_empty));
            isValid = false;
        }
        if ("".equals(selectedMark)) {
            mark.setError(getString(R.string.mark_not_empty));
            isValid = false;
        }

        if (selectedCrp != 3 &&
                selectedCrp != 6 &&
                selectedCrp != 9 &&
                selectedCrp != 15) {
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
            if(isParseable(selectedMark)){
                record.setMark(Integer.parseInt(selectedMark));
            } else {
                record.setMark(0);
            }
            // persist record and finish activity
            if (isUpdate) {
                new RecordDAO(this).update(record);
            } else {
                new RecordDAO(this).persist(record);
            }
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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

    private static boolean isParseable(String s){
        boolean parseable = true;
        try{
            Integer.parseInt(s);
        } catch (Exception e){
            parseable = false;
        }
        return parseable;
    }
    private static boolean isModuleNr(String s){
        return s.matches("[a-zA-Z]{2}[0-9]{4}");
    }
}

