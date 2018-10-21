package com.thm.ap.logic;

/*
This class contains the logic to analyse and calculate performance records
 */

public class Statistic {
    // ToDo
    private int recordCount = 0; // Anzahl gespeicherte Leistung
    private int creditPointsSum = 0; // Summe CP
    private int average = 0; // Durchschnittsnote
    private int requiredCreditPoints; // Benötigte Gesammtsumme
    private int fiftyPercentCount = 0;// Anzahl 50% Module

    // CP Gesamt - Summe = Noch notwendig
    public int getRemainingCreditPoints() {
        try {
            return requiredCreditPoints - creditPointsSum;
        } catch (NullPointerException e){
            throw new NullPointerException("no value for required credit points");
        }
    }

    public int getAverage () {
        // ToDo: Return weighted avarage grade in percent
        return 0;
    }


    // androidTest = In der app
    // tet = JUnit
}
