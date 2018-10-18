package de.thm.ap.persistence;

import de.thm.ap.records.model.Record;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import android.content.Context;
import java.util.List;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Optional;

/*
Records are being saved in this class and can be referenced when a new session starts.
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
    public List<Record> findAll() {
        // ToDo
        return null;
    }
    public Optional<Record> findById(int id) {
        // ToDo
        return null;
    }
    /**
     * Ersetzt das übergebene {@link Record} Objekt mit einem bereits gespeicherten {@link Record} Objekt mit gleicher id.
     *
     * @param record
     * @return true = update ok, false = kein {@link Record} Objekt mit gleicher id im Speicher gefunden
     */
    public boolean update(Record record) {
        // ToDo
        return true;
    }
    /**
     * Persistiert das übergebene {@link Record} Objekt und liefert die neue id zurück.
     *
     * @param record
     * @return neue record id
     */
    public int persist(Record record) {
        return 0;
    }
    @SuppressWarnings("unchecked")
    private void initRecords() {
        File f = ctx.getFileStreamPath(FILE_NAME);
        if (f.exists()) {
            try (FileInputStream in = ctx.openFileInput(FILE_NAME)) {
                Object obj = obj = new ObjectInputStream(in).readObject();
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
    private void saveRecords() {
        try (FileOutputStream out = ctx.openFileOutput(FILE_NAME, Context.
                MODE_PRIVATE)) {
            new ObjectOutputStream(out).writeObject(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

