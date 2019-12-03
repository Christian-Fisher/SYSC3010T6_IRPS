import RPi.GPIO as IO
import time
import socket

IO.setwarnings(False)
IO.setmode(IO.BCM)
IRPins = [12,13,14,15,16,17,18,19,20] # array of GPIO pins

Socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
receiveSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
receiveSocket.bind(("", 3001))
port =2001
local = "127.0.0.1"

IO.setup(12,IO.IN) #GPIO 12 -> IR sensorA0 as input
IO.setup(13,IO.IN) #GPIO 13 -> IR sensorA1 as input
IO.setup(14,IO.IN) #GPIO 14 -> IR sensorA2 as input
IO.setup(15,IO.IN) #GPIO 15 -> IR sensorB0 as input
IO.setup(16,IO.IN) #GPIO 16 -> IR sensorB1 as input
IO.setup(17,IO.IN) #GPIO 17 -> IR sensorB2 as input
IO.setup(18,IO.IN) #GPIO 18 -> IR sensorC0 as input
IO.setup(19,IO.IN) #GPIO 19 -> IR sensorC1 as input
IO.setup(20,IO.IN) #GPIO 20 -> IR sensorC2 as input

flag=[1,1,1,1,1,1,1,1,1] # initializing sensor states

def sendToMain(spot, occupancy):
    Socket.sendto(("IR:"+str(spot)+","+str(occupancy)).encode('utf-8'),(local, port))
    print("IR:"+str(spot)+","+str(occupancy))
while 1:# polling indefinitely
    time.sleep(2)
    for x in range(0,9): #loop through nine IR sensors
        if(IO.input(IRPins[x])!= flag[x]): #comparing current state with previous state
            flag[x] = (IO.input(IRPins[x])) # update flag element when state change occurs
            sendToMain(x, flag[x])
            
            
GPIO.cleanup() # Clearing GPIO setup at exit
        
