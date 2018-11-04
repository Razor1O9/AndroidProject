package de.thm.ap.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import de.thm.ap.records.model.Record;

@Database(entities = {Record.class}, version = 1)
public abstract class RecordDatabase extends RoomDatabase {
    public abstract RecordDAO recordDAO();
}
