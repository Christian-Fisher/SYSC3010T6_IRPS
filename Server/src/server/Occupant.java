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
public class Occupant {
    private String License;
    private int pin;
    private int SNum;
    public Occupant(String Licence, int pin, int SNum){
        this.License = Licence;
        this.SNum = SNum;
        this.pin = pin;
    }
    
    
    public String getLicense() {
        return License;
    }

    public void setLicense(String License) {
        this.License = License;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getSNum() {
        return SNum;
    }

    public void setSNum(int SNum) {
        this.SNum = SNum;
    }
}
