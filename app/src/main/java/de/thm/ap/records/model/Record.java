package de.thm.ap.records.model;

import java.io.Serializable;

/*
 / ToDo getter + setter ausfüllen
 / Die Record-Klasse beeinhaltet alle gültigen Elemente eines Records (Eintrag) im Formular.
 / Die Klasse definiert sowohl die Gültigkeit als auch die Ausgabe von Einträgen.
 */

public class Record implements Serializable {
    private String moduleName;
    private double moduleNum;
    private int id;
    private int year;
    private boolean summerTerm;
    private boolean halfweight;
    private int crp;
    private int mark;


    // ToDo
    public String setModuleName(String data) {
        moduleName = data;
        return moduleName;
    }

    // ToDo
    public String getModuleName() {
        return moduleName;
    }

    public double setModuleNum(double data) {
        moduleNum = data;
        return moduleNum;
    }

    public double getModuleNum() {
        return moduleNum;
    }

    public int setID(int id) {
        crp = id;
        return crp;
    }

    public int getId() {
        return crp;
    }

    public void setYear(int i) {
    }

    public long getYear() {

        return 0;
    }

    public void setSummerTerm(boolean b) {
    }

    public long isSummerTerm() {
        return 0;
    }

    public void setHalfWeighted(boolean b) {
    }

    public long isHalfWeighted() {
        return 0;
    }

    public void setCrp(int i) {
    }

    public long getCrp() {
        return 0;
    }

    public void setMark(int i) {
    }


    public long getMark() {
        return 0;
    }
}