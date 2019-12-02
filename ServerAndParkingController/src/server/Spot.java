/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Christian Fisher
 */
public class Spot {
    private boolean occupancy;
    private Occupant occupant;
    private String label="";
    public Spot(){
        occupancy = false;
        occupant = null;

    }

    public void setLabel(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return label;
    }
    
    public boolean getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(boolean occupancy) {
        this.occupancy = occupancy;
    }

    public Occupant getOccupant() {
        return occupant;
    }

    public void setOccupant(Occupant occupant) {
        this.occupant = occupant;
    }
    
}
