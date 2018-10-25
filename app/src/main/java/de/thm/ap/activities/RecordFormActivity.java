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

/*
 / This class contains to formula to enter a new record
 */

public class RecordFormActivity extends AppCompatActivity {
    private CheckBox term, halfweight;
    private EditText moduleNum, creditPoints, mark;
    private AutoCompleteTextView moduleName;
    private ListView year;

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
        ArrayAdapter<ListView> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getYears());
        year.setAdapter(adapter2);
    }

    private LinkedList getYears() {
        LinkedList yearList = new LinkedList();
        yearList.add(2018);
        yearList.add(2017);
        yearList.add(2016);
        yearList.add(2015);
        yearList.add(2014);
        yearList.add(2013);
        yearList.add(2012);
        yearList.add(2011);
        yearList.add(2010);
        yearList.add(2009);
        yearList.add(2008);
        yearList.add(2007);


        return yearList;
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

        record.setModuleName(moduleName.getText().toString().trim());
        if ("".equals(record.getModuleName())) {
            moduleName.setError(getString(R.string.module_name_not_empty));
            isValid = false;
        }
        if ("".equals(record.getModuleNum())) {
            moduleNum.setError(getString(R.string.module_number_not_empty));
            isValid = false;
        }
        if ("".equals(record.getModuleName())) {
            creditPoints.setError(getString(R.string.credit_points_not_empty));
            isValid = false;
        }
        if ("".equals(record.getModuleName())) {
            mark.setError(getString(R.string.mark_not_empty));
            isValid = false;
        }
        if ((Integer.parseInt(String.valueOf(record.getMark())) != 3) |
                (Integer.parseInt(String.valueOf(record.getMark())) != 6) |
                (Integer.parseInt(String.valueOf(record.getMark())) != 9) |
                (Integer.parseInt(String.valueOf(record.getMark())) != 15)) {
            creditPoints.setError(getString(R.string.credit_points_not_valid));
            isValid = false;
        }

        if ((Integer.parseInt(String.valueOf(record.getMark())) < 50) | (Integer.parseInt(String.valueOf(record.getMark())) > 100)) {
            mark.setError(getString(R.string.mark_not_valid));
            isValid = false;
        }
        // ToDo restliche Fehler testen
        if (isValid) {

            record.setModuleNum(selectedNum);
            record.setModuleName(selectedName);
            record.setYear(Integer.parseInt(selectedYear));
            record.setHalfWeighted(halfweight.isChecked());
            record.setSummerTerm(term.isChecked());
            record.setCrp(Integer.parseInt(String.valueOf(selectedCrp)));
            record.setMark(Integer.parseInt(String.valueOf(selectedMark)));
            record.setID(id);


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

