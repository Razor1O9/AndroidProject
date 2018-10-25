package de.thm.ap.activities;

import android.os.Bundle;

import de.thm.ap.R;

import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

import de.thm.ap.records.model.Record;
import de.thm.ap.persistence.RecordDAO;

import java.util.LinkedList;
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
    }

    public void onSave(View view) {
        Record record = new Record("", "", 0, false, false, 0, 0);

        String selectedName = moduleName.toString();
        String selectedNum = moduleNum.toString();
        String selectedYear = year.getSelectedItem().toString();
        Integer selectedCrp = Integer.valueOf(creditPoints.getText().toString());
        Integer selectedMark = Integer.valueOf(mark.getText().toString());
        int id = record.getId();

        Boolean isValid = true;
        record.setModuleNum(selectedNum);
        record.setModuleName(selectedName);
        record.setYear(Integer.parseInt(selectedYear));
        record.setHalfWeighted(halfweight.isChecked());
        record.setSummerTerm(term.isChecked());
        record.setCrp(Integer.parseInt(String.valueOf(selectedCrp)));
        record.setMark(Integer.parseInt(String.valueOf(selectedMark)));
        record.setID(id);


        if ("".equals(record.getModuleName())) {
            moduleName.setError(getString(R.string.module_name_not_empty));
            isValid = false;
        }
        if ("".equals(record.getModuleNum())) {
            moduleNum.setError(getString(R.string.module_number_not_empty));
            isValid = false;
        }
        if ("".equals(record.getCrp())) {
            creditPoints.setError(getString(R.string.credit_points_not_empty));
            isValid = false;
        }
        if ("".equals(record.getMark())) {
            mark.setError(getString(R.string.mark_not_empty));
            isValid = false;
        }
        if ((Integer.parseInt(String.valueOf(record.getCrp())) != 3) |
                (Integer.parseInt(String.valueOf(record.getCrp())) != 6) |
                (Integer.parseInt(String.valueOf(record.getCrp())) != 9) |
                (Integer.parseInt(String.valueOf(record.getCrp())) != 15)) {
            creditPoints.setError(getString(R.string.credit_points_not_valid));
            isValid = false;
        }

        if ((Integer.parseInt(String.valueOf(record.getMark())) < 50) | (Integer.parseInt(String.valueOf(record.getMark())) > 100)) {
            mark.setError(getString(R.string.mark_not_valid));
            isValid = false;
        }
        // ToDo restliche Fehler testen
        if (isValid) {

            // persist record and finish activity
            new RecordDAO(this).persist(record);
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
}

