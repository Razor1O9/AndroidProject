package de.thm.ap.logic;

/*
 / This class contains the logic to analyse and calculate performance records
 */

import java.util.List;

import de.thm.ap.records.model.Record;

public class Stats {
    private final List<Record> records;
    private int creditPointsSum = 0; // Summe CP
    private int average = 0; // Durchschnittsnote
    private int fiftyPercentCount = 0;// Anzahl 50% Module
    public final int requiredCreditPoints  = 180;

    public Stats(List<Record> records) {
        this.records = records;
    }

    public int getSumCrp() {
        creditPointsSum = 0;
        for (Record recordItem : records) {
                if(recordItem.getMark() >= 50)
                    creditPointsSum += recordItem.getCrp();
        }
        return creditPointsSum;
    }

    public int getCrpToEnd() {
        return requiredCreditPoints - getSumCrp();
    }

    public int getSumHalfWeighted() {
        fiftyPercentCount = 0;
        for (Record recordItemHalf : records) {
            if (recordItemHalf.isHalfWeighted())
                fiftyPercentCount += 1;
        }
        return fiftyPercentCount;
    }

    public int getAverageMark() {
        int averageMarkFullWeight = 0;
        int averageMarkHalfWeight = 0;
        int ungradedRecords = 0;
        for (Record recordItem : records) {
            if (recordItem.isHalfWeighted())
                averageMarkHalfWeight += recordItem.getMark();
            else averageMarkFullWeight += recordItem.getMark();

            if(recordItem.getMark() < 50)
                ungradedRecords++;
        }
        return (int) Math.round((averageMarkFullWeight + (averageMarkHalfWeight*0.5)) / ((records.size() - ungradedRecords) - (getSumHalfWeighted()*0.5) ));
    }

}
