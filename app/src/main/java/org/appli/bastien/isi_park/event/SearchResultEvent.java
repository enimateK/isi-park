package org.appli.bastien.isi_park.event;

import org.appli.bastien.isi_park.model.Parking;

import java.util.ArrayList;
import java.util.List;

public class SearchResultEvent {
    private List<Parking> parkings;

    public SearchResultEvent(List<Parking> parks){
        this.parkings = parks;
    }

    public List<Parking> getParkings(){
        return parkings;
    }

    public Parking getParking(String idobj) {
        for(Parking parking : getParkings()) {
            if(parking.idobj.equals(idobj)) {
                return parking;
            }
        }
        return null;
    }

    public List<Parking> getFavorites() {
        List<Parking> tmp = new ArrayList<>();
        for(Parking parking : getParkings()) {
            if(parking.favorite) {
                tmp.add(parking);
            }
        }
        return tmp;
    }

    public List<Parking> getParkings(String filter) {
        List<Parking> tmp = new ArrayList<>();
        for(Parking parking : getParkings()) {
            if(parking.name.contains(filter)) {
                tmp.add(parking);
            }
        }
        return tmp;
    }

    public List<Parking> getParkings(String name, String adresse, Integer dispo, Boolean cb, Boolean espece, Boolean total_gr) {
        List<Parking> tmp = new ArrayList<>();
        for(Parking parking : getParkings()) {
            if(name != null && !parking.name.contains(name))
                continue;
            if(adresse != null && !parking.adresse.contains(adresse))
                continue;
            if(dispo != null && parking.dispoVoitures < dispo)
                continue;
            if(cb != null && parking.cb != cb)
                continue;
            if(espece != null && parking.espece != espece)
                continue;
            if(total_gr != null && parking.totalGr != total_gr)
                continue;
            tmp.add(parking);
        }
        return tmp;
    }
}
