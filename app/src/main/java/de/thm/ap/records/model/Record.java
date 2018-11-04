package de.thm.ap.records.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/*
 / Die Record-Klasse beeinhaltet alle gültigen Elemente eines Records (Eintrag) im Formular.
 / Die Klasse definiert sowohl die Gültigkeit als auch die Ausgabe von Einträgen.
 */

@Entity
public class Record implements Serializable {
    @PrimaryKey(autoGenerate=true)
    private int id;

    @ColumnInfo (name = "module_name")
    private String moduleName;

    @ColumnInfo (name = "module_number")
    private String moduleNum;

    @ColumnInfo (name = "mark")
    private int mark;

    @ColumnInfo (name = "year")
    private int year;

    @ColumnInfo (name = "crp")
    private int crp;

    @ColumnInfo (name = "summer_term")
    private boolean summerTerm;

    @ColumnInfo (name = "half_weight")
    private boolean halfWeight;


    public Record (String moduleNum, String moduleName, int year, boolean summerTerm, boolean halfWeight, int crp, int mark){
        this.moduleNum = moduleNum;
        this.moduleName = moduleName;
        this.year = year;
        this.summerTerm = summerTerm;
        this.halfWeight = halfWeight;
        this.crp = crp;
        this.mark = mark;
    }

    public void setModuleName(String data) {
        moduleName = data;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleNum(String data) {
        moduleNum = data;
    }

    public String getModuleNum() {
        return moduleNum;
    }

    public void setID(int inputID) {
        id = inputID;
    }

    public Integer getId() {
        return id;
    }

    public void setYear(int inputYear) {
        year = inputYear;
    }

    public long getYear() {
        return year;
    }

    public void setSummerTerm(boolean term) {
        summerTerm = term;
    }

    public boolean isSummerTerm() {
        return summerTerm;
    }

    public void setHalfWeighted(boolean half) {
        halfWeight = half;
    }

    public boolean isHalfWeighted() {
        return halfWeight;
    }

    public void setCrp(int cp) {
        crp = cp;
    }

    public long getCrp() {
        return crp;
    }

    public void setMark(int input) {
        mark = input;
    }


    public int getMark() {
        return mark;
    }

    @Override
    public String toString(){
        return moduleName + " " + moduleNum + " " + "(" + (mark == 0 ? "-/-" : mark + "%")
                + " " + crp + "crp)";
    }
}