package org.appli.bastien.isi_park.model;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

//@Table(name = "Parking")
public class Parking {

    //@Column(name = "name")
    private String name;
    //@Column(name = "description")
    private String description;

    //@Column(name = "latitude")
    private Double latitude;
    //@Column(name = "longitude")
    private Double longitude;

    //@Column(name = "voiturePlace")
    private Integer voiturePlace;
    //@Column(name = "motoPlace")
    private Integer motoPlace;
    //@Column(name = "veloPlace")
    private Integer veloPlace;

    //@Column(name = "favorite")
    private Boolean favorite;

    //@Column(name = "label", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String label;

    public Parking (String name, String description, Double longitude, Double latitude, Integer voiturePlace, Integer motoPlace, Integer veloPlace, String label, Boolean favorite){
        super();
        this.name = name;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.voiturePlace = voiturePlace;
        this.motoPlace = motoPlace;
        this.veloPlace = veloPlace;
        this.favorite = favorite;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getVoiturePlace() {
        return voiturePlace;
    }

    public void setVoiturePlace(Integer voiturePlace) {
        this.voiturePlace = voiturePlace;
    }

    public Integer getMotoPlace() {
        return motoPlace;
    }

    public void setMotoPlace(Integer motoPlace) {
        this.motoPlace = motoPlace;
    }

    public Integer getVeloPlace() {
        return veloPlace;
    }

    public void setVeloPlace(Integer veloPlace) {
        this.veloPlace = veloPlace;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
