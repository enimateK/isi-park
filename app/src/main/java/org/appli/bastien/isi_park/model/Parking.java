package org.appli.bastien.isi_park.model;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

//@Table(name = "Parking")
public class Parking extends Model {
    public static List<Parking> parkings;
    public static Parking getParking(String id) {
        for(Parking parking : parkings) {
            if(parking.id.equals(id))
                return parking;
        }
        return new Parking();
    }
    @Column(name = "id", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;
    @Column(name = "name")
    public String name;
    @Column(name = "description")
    public String description;

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
    @Column(name = "dispoVoitures")
    public int dispoVoitures;

    @Column(name = "favorite")
    public boolean favorite;
}
