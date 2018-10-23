package de.thm.ap.records.model;

import java.io.Serializable;

/*
 / Die Record-Klasse beeinhaltet alle gültigen Elemente eines Records (Eintrag) im Formular.
 / Die Klasse definiert sowohl die Gültigkeit als auch die Ausgabe von Einträgen.
 */

public class Record implements Serializable {
    private String moduleName;
    private double moduleNum;
    private int id;
    private int year;
    private int crp;
    private int mark;
    private boolean summerTerm;
    private boolean halfWeight;

    public Record(String cs1013, String objektorientierte_programmierung, int i, boolean b, boolean b1, int i1, int i2) {
    }

    public void setModuleName(String data) {
        moduleName = data;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleNum(double data) {
        moduleNum = data;
    }

    public double getModuleNum() {
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