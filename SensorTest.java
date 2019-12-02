
package IRPStest;

import IRPS.IrSensor;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shan Rameshkanna (T6)
 */
public class SensorTest {
    
    public void inSpot() {
       IrSensor sensor = new IrSensor ( 2, 20, true);
        Assert.assertEquals(1,sensor.carDetect(10));
    } 
    public void tooClose() {
       IrSensor sensor = new IrSensor ( 2, 20, true);
        Assert.assertEquals(2,sensor.carDetect(1));
    }
    public void notinSpot() {
       IrSensor sensor = new IrSensor ( 2, 20, true);
        Assert.assertEquals(3,sensor.carDetect(25));
    }
    public void noPower() {
       IrSensor sensor = new IrSensor ( 2, 20, false);
        Assert.assertEquals(0,sensor.carDetect(15));
    }
    
    
}
