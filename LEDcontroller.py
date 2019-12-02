import time
#import RPi.GPIO as GPIO
import socket

#GPIO.setmode(GPIO.BCM)
#GPIO.setwarnings(False)

def toggleLED(index, state): #method to toggle indexed RG-LED pair
    if(state): # If state = true R-LED on & G-LED off -> indexed spot occupied/booked
 #       GPIO.output (ledPins[index], GPIO.HIGH)
        print("spot"+str(index)+" is occupied")
    if(state==False): # If state = false R-LED off & G-LED on -> indexed spot is empty/bookig timeout
  #      GPIO.output (ledPins[index], GPIO.LOW)
        print("spot"+str(index)+" is empty")

        


ledPins = [2,3,4,5,6,7,8,9,10] # arrays of GPIO pins as output
        
receiveSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
receiveSocket.bind(("", 3000))

while(True):
    print("test")
    data, address = receiveSocket.recvfrom(3000)   
    print("received")
    message = str(data).split(":")[1]
    indexToChange = message.split(",")[0]
    stateString = message.split(",")[1]
    if(stateString == "true"):
        stateToChange = True
    else:
        stateToChange = False
    
    toggleLED(indexToChange, stateToChange)
#for i in range (0,2):
#    GPIO.setup(ledPins[i], GPIO.OUT)

#GPIO.setup(2,GPIO.OUT)  #GPIO pin2  -> A0-RG-LED as output
#GPIO.setup(3,GPIO.OUT)  #GPIO pin3  -> A1-RG-LED as output
#GPIO.setup(4,GPIO.OUT)  #GPIO pin4  -> A2-RG-LED as output
#GPIO.setup(5,GPIO.OUT)  #GPIO pin5  -> B0-RG-LED as output
#GPIO.setup(6,GPIO.OUT)  #GPIO pin6  -> B1-RG-LED as output
#GPIO.setup(7,GPIO.OUT)  #GPIO pin7  -> B2-RG-LED as output
#GPIO.setup(8,GPIO.OUT)  #GPIO pin8  -> C0-RG-LED as output
#GPIO.setup(9,GPIO.OUT)  #GPIO pin9  -> C1-RG-LED as output
#GPIO.setup(10,GPIO.OUT) #GPIO pin10 -> C2-RG-LED as output



GPIO.cleanup()

    