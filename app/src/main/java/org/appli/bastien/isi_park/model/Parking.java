package org.appli.bastien.isi_park.model;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Parking")
public class Parking extends Model {
    @Column(name = "idobj", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String idobj;
    @Column(name = "name")
    public String name;
    @Column(name = "description")
    public String description;
    @Column(name = "adresse")
    public String adresse;
    @Column(name = "codePostal")
    public String codePostal;
    @Column(name = "ville")
    public String ville;

    @Column(name = "latitude")
    public Double latitude;
    @Column(name = "longitude")
    public Double longitude;

    @Column(name = "placesVoitures")
    public int placesVoitures;
    @Column(name = "placesMoto")
    public int placesMoto;
    @Column(name = "placesVelo")
    public int placesVelo;
    @Column(name = "placesPmr")
    public int placesPmr;
    @Column(name = "placesVoituresElectriques")
    public int placesVoituresElectriques;
    @Column(name = "dispoVoitures")
    public int dispoVoitures;

    @Column(name = "cb")
    public boolean cb;
    @Column(name = "espece")
    public boolean espece;
    @Column(name = "totalGr")
    public boolean totalGr;

    @Column(name = "favorite")
    public boolean favorite;

    public Parking() {
        super();
    }

    public Parking(String idobj) {
        super();
        this.idobj = idobj;
    }
}
