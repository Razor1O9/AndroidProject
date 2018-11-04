package de.thm.ap.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.thm.ap.records.model.Record;

@Dao
public interface RecordDAO {
    @Query("SELECT * FROM record")
    List<Record> findAll();

    @Insert
    void persist(Record record);

    @Update
    boolean update(Record record);

    @Query("SELECT * FROM record WHERE record.id=id") //TODO Macht das Sinn?
    Optional<Record> findById(int id);

    @Delete
    boolean delete(Record delRecord);

    @Delete
    boolean delete(List<Record> recordListToDelete);
}
