package de.thm.ap.records.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

    @Entity
    public class Module implements Serializable {

        private @PrimaryKey(autoGenerate = true) int id;
        private String nr, name;
        private int crp;

        public Module(String nr, String name, int crp){
            this.nr = nr.toUpperCase();
            this.name = name;
            this.crp = crp;
        }

        public void setId(int id){
            this.id = id;
        }

        public int getId() {
            return id;
        }

    /*
    public void setNr(String nr){
        this.nr = nr.toUpperCase();
    }
    */

        public String getNr(){
            return nr;
        }

        public void setName(String name){ this.name = name; }

        public String getName(){
            return name;
        }

/*
    public void setCrp(int crp) {
        this.crp = crp;
    }
*/

        public int getCrp() {
            return crp;
        }

        @Override
        public String toString(){
            return name;
        }
    }

