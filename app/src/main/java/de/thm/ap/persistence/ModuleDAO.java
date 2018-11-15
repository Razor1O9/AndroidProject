package de.thm.ap.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
import java.util.Optional;

import de.thm.ap.records.model.Module;
import de.thm.ap.records.model.Record;

@Dao
public interface ModuleDAO {
    @Query("SELECT * FROM record")
    List<Record> findAllModules();

    @Insert
    void persistAllModules(Module[] record);

    @Query("SELECT * FROM record WHERE id = :id")
    Optional<Record> findModuleById(int id);

    @Delete
    void deleteAllModules();
}
