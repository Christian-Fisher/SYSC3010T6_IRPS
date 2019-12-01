import RPi.GPIO as IO
import time

IO.setwarnings(False)
IO.setmode(IO.BCM)
IRPins = [12,13,14,15,16,17,18,19,20] # array of GPIO pins

IO.setup(12,IO.IN) #GPIO 12 -> IR sensorA0 as input
IO.setup(13,IO.IN) #GPIO 13 -> IR sensorA1 as input
IO.setup(14,IO.IN) #GPIO 14 -> IR sensorA2 as input
IO.setup(15,IO.IN) #GPIO 15 -> IR sensorB0 as input
IO.setup(16,IO.IN) #GPIO 16 -> IR sensorB1 as input
IO.setup(17,IO.IN) #GPIO 17 -> IR sensorB2 as input
IO.setup(18,IO.IN) #GPIO 18 -> IR sensorC0 as input
IO.setup(19,IO.IN) #GPIO 19 -> IR sensorC1 as input
IO.setup(20,IO.IN) #GPIO 20 -> IR sensorC2 as input

flag=[0,0,0,0,0,0,0,0,0] # initializing sensor states

while 1:# polling indefinitely
    time.sleep (1) # poll interval set to 1 second
    i=0
    x=0
    for x in range(0,9): #loop through nine IR sensors
        if(IO.input(IRPins[x])!= flag[x]): #comparing current state with previous state
            flag[x] = (IO.input(IRPins[x])) # update flag element when state change occurs
    for i in range(0,9):
        flag[i] = int(not flag[i]) # inverting flag elements to show 0=empty;1=occupied
    print(flag) #printing overall instantaneous states of the nine spots
            #UDP
GPIO.cleanup() # Clearing GPIO setup at exit
        
