# SYSC3010T6_IRPS

This is the T6 IRPS Term Project. 
This project consists of 5 main programs. The Mobile Application, the Main Server, the The ParkingController, the IRPoller, the LEDController and the Arduino manager.

The IP's for each device are as follows:
Server Pi:192.168.0.180
ParkingController: 192.168.0.181
App: 192.168.0.190

The lot is functional without the application, but will run at half speed.
UDP losses are handled through the use of Queues. Only verified commands are removed from the queue, which allows the system to not drop any commands.

