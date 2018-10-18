package de.thm.ap.records.model;

import java.io.Serializable;

/*
 // ToDo getter + setter ausfüllen
 */

public class Record implements Serializable {

    // ToDo
    public String setModuleName(String data) {
        return data;
    }

    // ToDo
    public String getModuleName(String data) {
        String x = data;
        return x;
    }

    public String setModuleNum(String data) {
        String y = data;
        return y;
    }

    public static int getId(Record record) {
        return 0;
    }

    public void setYear(int i) {
    }

    public void setSummerTerm(boolean b) {
    }

    public void setHalfWeighted(boolean b) {
    }

    public void setCrp(int i) {
    }

    public void setMark(int i) {
    }

    public double getModuleNum() {
        return 0;
    }

    public long getYear() {
        return 0;
    }

    public long isSummerTerm() {
        return 0;
    }

    public long isHalfWeighted() {
        return 0;
    }

    public long getCrp() {
        return 0;
    }

    public long getMark() {
        return 0;
    }
}