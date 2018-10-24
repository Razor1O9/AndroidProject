package de.thm.ap.logic;

/*
 / This class contains the logic to analyse and calculate performance records
 */

import java.util.List;

import de.thm.ap.records.model.Record;

public class Stats {
    private final List<Record> records;

    // ToDo Werte füllen
    private int recordCount = 0; // Anzahl gespeicherte Leistung
    private int creditPointsSum = 0; // Summe CP
    private int average = 0; // Durchschnittsnote
    private int fiftyPercentCount = 0;// Anzahl 50% Module
    public final int requiredCreditPoints  = 180;

    public Stats(List<Record> records) {
        this.records = records;
    }

    public int getSumCrp() {
        for (Record recordItem : records) {
            creditPointsSum += recordItem.getCrp();
        }
        return creditPointsSum;
    }

    public int getCrpToEnd() {
        // CP Gesamt - Summe = Noch benötigte Crps

        return requiredCreditPoints - getSumCrp();
    }

    /*public int getSumHalfWeighted() {
        int sumHalfWeighted = 0;
        for (Record recordItemHalf : records) {
            if (recordItemHalf.isHalfWeighted())
                sumHalfWeighted += recordItemHalf.getCrp();
        }
        return sumHalfWeighted;
    }*/

    public int getSumHalfWeighted() {
        for (Record recordItemHalf : records) {
            if (recordItemHalf.isHalfWeighted())
                fiftyPercentCount += 1;
        }
        return fiftyPercentCount;
    }

    public int getAverageMark() {
        int averageMarkFullWeight = 0;
        int averageMarkHalfWeight = 0;
        for (Record recordItem : records) {
            if (recordItem.isHalfWeighted())
                averageMarkHalfWeight += recordItem.getMark();
            else averageMarkFullWeight += recordItem.getMark();
        }

        return (int)Math.round((averageMarkFullWeight + (averageMarkHalfWeight*0.5)) / (records.size() - (getSumHalfWeighted()*0.5) ));
    }

}
