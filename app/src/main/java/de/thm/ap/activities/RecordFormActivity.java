package de.thm.ap.activities;

import android.os.Bundle;

import de.thm.ap.R;

import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
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
    private EditText moduleNum, creditPoints, mark;
    private AutoCompleteTextView moduleName;
    private ListView year;
    private Boolean term, halfweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_form);

        // Show up button in action bar
        Optional.ofNullable(getSupportActionBar()).ifPresent(actionBar -> actionBar.setDisplayHomeAsUpEnabled(true));

        // get views
        moduleNum = findViewById(R.id.module_num);

        // configure suggestions in auto complete text view
        String[] names = getResources().getStringArray(R.array.module_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
        moduleName.setAdapter(adapter);

        // configure year spinner
        ArrayAdapter<LinkedList> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getYears());
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
        Record record = new Record("","", 0, false,false,0,0);
        // validate user input

        String selectedName = moduleName.toString();
        String selectedNum = moduleNum.toString();
        String selectedYear = year.getSelectedItem().toString();
        Boolean selectedTerm = term.booleanValue();
        Boolean selectedWeight = halfweight.booleanValue();
        Integer selectedCrp = Integer.valueOf(creditPoints.getText().toString());
        Integer selectedMark = Integer.valueOf(mark.getText().toString());

        Boolean isValid = true;

        record.setModuleName(moduleName.getText().toString().trim());
        if ("".equals(record.getModuleName())) {
            moduleName.setError(getString(R.string.module_name_not_empty));
            isValid = false;
        }
        if ("".equals(record.getModuleName())) {
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
                (Integer.parseInt(String.valueOf(record.getMark())) != 30)){
                    creditPoints.setError(getString(R.string.credit_points_not_valid));
                    isValid = false;
        }

        if ((Integer.parseInt(String.valueOf(record.getMark())) < 50) | (Integer.parseInt(String.valueOf(record.getMark())) > 100)) {
            isValid = false;
            mark.setError(getString(R.string.mark_not_valid));
            isValid = false;
        }

        if (isValid) {

            record.setModuleNum(selectedNum);
            record.setModuleName(selectedName);
            record.setYear(Integer.parseInt(selectedYear));
            record.setHalfWeighted(halfweight);
            record.setSummerTerm(term);
            record.setCrp(Integer.parseInt(creditPoints.getText().toString()));
            record.setMark(Integer.parseInt(mark.getText().toString()));



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

