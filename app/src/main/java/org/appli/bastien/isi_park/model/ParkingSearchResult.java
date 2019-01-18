package org.appli.bastien.isi_park.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ParkingSearchResult {
    @Expose
    public List<ParkingRecord> records;
    public class ParkingRecord {
        @Expose
        public ParkingFields fields;
        public class ParkingFields {
            @Expose
            public String idobj;
            @Expose
            public String nom_complet;
            @Expose
            public String presentation;
            @Expose
            public String adresse;
            @Expose
            public String code_postal;
            @Expose
            public String commune;
            @Expose
            public String moyen_paiement;
            @Expose
            public int capacite_voiture;
            @Expose
            public int capacite_vehicule_electrique;
            @Expose
            public int capacite_pmr;
            @Expose
            public int capacite_moto;
            @Expose
            public int capacite_velo;
            @Expose
            public List<Double> location;
        }
    }
}
