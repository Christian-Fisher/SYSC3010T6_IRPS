# SYSC3010T6_IRPS

This is the T6 IRPS Term Project. 
This project consists of 5 main programs. The Mobile Application, the Main Server, the The ParkingController, the IRPoller, the LEDController and the Arduino manager.

The IP's for each device are as follows:
Server Pi:192.168.0.180
ParkingController: 192.168.0.181
App: 192.168.0.190

The lot is functional without the application, but will run at half speed.
UDP losses are handled through the use of Queues. Only verified commands are removed from the queue, which allows the system to not drop any commands.

To setup this system, all that is required is the following:	(Assuming hardware is complete and connected)
1. Configure all devices to above IPs, and connect them to the same network
2. Run ServerAndParkingController.jar located in ServerAndParkingController/dist on the Server
3. run IRPoller.py and LEDController.py on the ParkingController
4. Run ArduinoUDP.jar in ArduinoUDP/dist on the ParkingController
5. Run ParkingControllerMain.class by running the following command in /ServerAndParkingController/src
	"sudo java -cp . parkingController.ParkingControllerMain
	This command runs the .class version of the parking controller code, and the classpath is needed to allow the system to find the correct path
6. Install the anrdoid app on a phone.
7. Enjoy!

