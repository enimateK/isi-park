package org.appli.bastien.isi_park.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DispoSearchResult {
    @Expose
    public List<AvailabilityRecord> records;
    public class AvailabilityRecord {
        @Expose
        public AvailabilityFields fields;
        public class AvailabilityFields {
            @Expose
            public String idobj;
            @Expose
            public int grp_disponible;
            @Expose
            public int grp_statut;
        }
    }
}
