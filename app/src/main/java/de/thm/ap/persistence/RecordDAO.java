package de.thm.ap.persistence;

import de.thm.ap.records.model.Record;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import android.content.Context;

import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

/*
 / Records are being saved in this class and can be referenced when a new session starts.
 */

public class RecordDAO {
    private static String FILE_NAME = "records.obj";
    private Context ctx;
    private List<Record> records;
    private int nextId = 1;
    public RecordDAO(Context ctx) {
        this.ctx = ctx;
        initRecords();
    }

    /**
     * Kein Parameter, schlichter Aufruf.
       @return Records
     */
    public List<Record> findAll() {
        return records;
    }

    /**
       Gibt Record mit der angegebenen ID zurück
       oder Null falls kein mit dieser ID gefunden wurde (=Optional)
       @return Record
     */
    public Optional<Record> findById(int id) {
        for (Record recordItem : records) {
            if (recordItem.getId().equals(id))
                return Optional.of(recordItem);
        }
        return Optional.empty();
    }
    /**
     * Ersetzt das übergebene {@link Record} Objekt mit einem bereits gespeicherten {@link Record} Objekt mit gleicher id.
     *
     * @param record
     * @return true = update ok, false = kein {@link Record} Objekt mit gleicher id im Speicher gefunden
     */
    public boolean update(Record record) {
        delete(Arrays.asList(record));
        records.add(record);
        saveRecords();
//        if (findById(record.getId()).isPresent()){
//            findById(record.getId()).get(); // findById = optional wrapped, get holt aus dem optional das objekt+
//            for (Record recordItem : records) {
//                if (recordItem.getId().equals(record.getId())) {
//                    recordItem = record;
//                    saveRecords();
//                }
//            }
//            return true;
//        }
        return true;
    }

    /**
     * Löscht das übergebene {@link Record} Objekt
     *
     * @param List<Record>
     * @return true = delete ok, false = löschen Fehlgeschlagen
     */
    public boolean delete(List<Record> recordListToDelete) {
        for (Record recordToDelete : recordListToDelete) {
            records.remove(findById(recordToDelete.getId()));
            saveRecords();
        }
        return true;
    }

    /**
     * Persistiert das übergebene {@link Record} Objekt und liefert die neue id zurück.
     *
     * @param record
     * @return neue record id
     */
    public int persist(Record record) {
        record.setID(nextId);
        records.add(record);
        saveRecords();
        return nextId++;
    }

    @SuppressWarnings("unchecked")
    /**
     * Liest aus der Datei, erstellt die ArrayList für Records und nextID wird für jeden Record erhöht.
     * Wird beim öffnen der App aufgerufen, damit nextId nicht immer auf 1 ist.
     */
    private void initRecords() {
        File f = ctx.getFileStreamPath(FILE_NAME);
        if (f.exists()) {
            try (FileInputStream in = ctx.openFileInput(FILE_NAME)) {
                Object obj = new ObjectInputStream(in).readObject();
                records = (List<Record>) obj;
                // init next id
                records.stream()
                        .mapToInt(Record::getId)
                        .max()
                        .ifPresent(id -> nextId = id + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            records = new ArrayList<>();
        }
    }

    /**
     * Speichert die Records in eine Datei die record.obj heißt
     */
    private void saveRecords() {
        try (FileOutputStream out = ctx.openFileOutput(FILE_NAME, Context.
                MODE_PRIVATE)) {
            new ObjectOutputStream(out).writeObject(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

