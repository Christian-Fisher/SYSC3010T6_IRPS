package IRPS;
import java.util.concurrent.Callable;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;

/**
 * This code demonstrates how to test the IR sensor(s)
 *
 * 
 */
public class Driver {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("irDriver test started");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput sensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,
                                                  PinPullResistance.PULL_DOWN);

        System.out.println("Waiting for object to be placed in front of sensor");

        // setup gpio pin #04 as an output pin and make sure they are all LOW at startup
        GpioPinDigitalOutput pinOut = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "out", PinState.LOW)
			;
        // create a gpio control trigger on the input pin ; when the input goes HIGH, also set gpio pin #04 to HIGH
        sensor.addTrigger(new GpioSetStateTrigger(PinState.HIGH, pinOut, PinState.HIGH));
        System.out.print ("sensor pass the test");

        // create a gpio control trigger on the input pin ; when the input goes LOW, also set gpio pin #04 to LOW
        sensor.addTrigger(new GpioSetStateTrigger(PinState.LOW, pinOut, PinState.LOW));
        System.out.print ("sensor fail the test");
       
        //program runs until user aborts (CTRL-C)
        while (true) {
            Thread.sleep(1000);
        }

    }
}
    
