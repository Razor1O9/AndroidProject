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

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleNum(String moduleNum) {
        this.moduleNum = moduleNum;
    }

    public String getModuleNum() {
        return moduleNum;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setSummerTerm(boolean summerTerm) {
        this.summerTerm = summerTerm;
    }

    public boolean isSummerTerm() {
        return summerTerm;
    }

    public void setHalfWeighted(boolean halfWeight) {
        this.halfWeight = halfWeight;
    }

    public boolean getHalfWeight() {
        return halfWeight;
    }

    public void setCrp(int crp) {
        this.crp = crp;
    }

    public int getCrp() {
        return crp;
    }

    public void setMark(int mark) {
        this.mark = mark;
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