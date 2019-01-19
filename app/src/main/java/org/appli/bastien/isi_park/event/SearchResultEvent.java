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
            if(parking.name.toLowerCase().contains(filter.toLowerCase().trim())) {
                tmp.add(parking);
            }
        }
        return tmp;
    }

    public List<Parking> getParkings(String recherche, String name, String adresse, int dispo, boolean cb, boolean espece, boolean total_gr) {
        List<Parking> tmp = new ArrayList<>();
        for(Parking parking : getParkings()) {
            if(!recherche.equals("") && !parking.name.toLowerCase().contains(recherche.toLowerCase().trim()))
                continue;
            if(!name.equals("") && !parking.name.toLowerCase().contains(name.toLowerCase().trim()))
                continue;
            if(!adresse.equals("") && !parking.adresse.toLowerCase().contains(adresse.toLowerCase().trim()))
                continue;
            if(dispo != 0 && parking.dispoVoitures < dispo)
                continue;
            if(cb != false && parking.cb != cb)
                continue;
            if(espece != false && parking.espece != espece)
                continue;
            if(total_gr != false && parking.totalGr != total_gr)
                continue;
            tmp.add(parking);
        }
        return tmp;
    }
}
