package de.thm.ap.records.model;

import java.io.Serializable;

/*
 / Die Record-Klasse beeinhaltet alle gültigen Elemente eines Records (Eintrag) im Formular.
 / Die Klasse definiert sowohl die Gültigkeit als auch die Ausgabe von Einträgen.
 */

public class Record implements Serializable {
    private String moduleName;
    private String moduleNum;
    private int id;
    private int year;
    private int crp;
    private int mark;
    private boolean summerTerm;
    private boolean halfWeight;


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

    public int getId() {
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


    public long getMark() {
        return mark;
    }
}