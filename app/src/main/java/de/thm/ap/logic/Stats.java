package de.thm.ap.logic;

/*
 / This class contains the logic to analyse and calculate performance records
 */

import java.util.List;

import de.thm.ap.records.model.Record;

public class Stats {
    // ToDo
    private int recordCount = 0; // Anzahl gespeicherte Leistung
    private int creditPointsSum = 0; // Summe CP
    private int average = 0; // Durchschnittsnote
    private int requiredCreditPoints; // Benötigte Gesammtsumme
    private int fiftyPercentCount = 0;// Anzahl 50% Module

    public Stats(List<Record> records) {
    }

    public int getSumCrp() {
        // ToDo
        return 0;
    }

    public int getCrpToEnd() {  // CP Gesamt - Summe = Noch notwendig
        try {
            return requiredCreditPoints - creditPointsSum;
        } catch (NullPointerException e){
            throw new NullPointerException("no value for required credit points");
        }
    }

    public int getSumHalfWeighted() {
        // ToDo
        return 0;
    }

    public int getAverageMark() {
        // ToDo: Return weighted avarage grade in percent
        return 0;
    }

}
