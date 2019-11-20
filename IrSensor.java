
package IRPS;

/**
 *
 * @author Shan Rameshkanna (T6)
 */
public class IrSensor {
    int minRange;
    int maxRange;
    boolean power;
    
    public IrSensor (int min, int max, boolean pow){
        this.minRange = min; //adequate range for IR detection is defined
        this.maxRange = max;
        this.power = pow;
    }
    public int carDetect( int rangeCar){
        if (power = true){ // sensor has power
            if ( rangeCar <= maxRange || rangeCar >= minRange){ //object is in adequate range
            System.out.println("A car is in the spot");
            return 1;
            }
            else if (rangeCar <= minRange){ //object is too close to sensor
                System.out.println("The car is too close to the sensor");
                return 2;
            }
            else { //object is well beyond the dtectable range
            System.out.println("No car is in the spot yet");
            return 3;
            }
        }
        else{ //sensor lost power
            System.out.println("The IR sensor has no power");
            return 0;
            
        }
        //return "";
    }
}

                    
                    
                    
        
        
    
    
    

