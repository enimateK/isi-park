package org.appli.bastien.isi_park.event;

import org.appli.bastien.isi_park.model.Parking;

import java.util.List;

public class SearchResultEvent {
    private List<Parking> parkings;

    public SearchResultEvent(List<Parking> parks){
        this.parkings = parks;
    }

    public List<Parking> getParkings(){
        return parkings;
    }
}
