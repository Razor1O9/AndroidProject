package de.thm.ap.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.thm.ap.records.model.Module;
import de.thm.ap.records.model.Record;

@Database(entities = {Record.class, Module.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract ModuleDAO moduleDAO();

    public abstract RecordDAO recordDAO();

    public static AppDatabase getDb(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public static AppDatabase getModuleDb(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
