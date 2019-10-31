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
public class MainTest {
    public static void main(String[] args){
        Spot lot[] = new Spot[9];
        for(int i =0; i<9; i++){
            lot[i]=new Spot();
            lot[i].setLabel(Integer.toString(i+1));
            System.out.println(lot[i].getLabel());
        }
        ServerUDP udp = new ServerUDP();
        
    }
}
