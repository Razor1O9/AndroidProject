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
    @Query("SELECT * FROM module")
    List<Module> findAllModules();

    @Insert
    void persistAllModules(Module[] modules);

    @Query("SELECT * FROM module WHERE id = :id")
    Optional<Module> findModuleById(int id);

    @Query("DELETE FROM module")
    void deleteAllModules();
}
