import time
import RPi.GPIO as GPIO
import socket

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
ledPins = [2,3,4,5,6,7,8,9,10] # arrays of GPIO pins as output
        
receiveSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
receiveSocket.bind(("", 3000))
for i in range (0,9):
    GPIO.setup(ledPins[i], GPIO.OUT) // setting GPIO pins as output to all LEDs
    GPIO.output(ledPins[i], GPIO.LOW) // initialize all LEDs to green
    
def toggleLED(index, state): #method to toggle indexed RG-LED pair
    if(state): # If state = true R-LED on & G-LED off -> indexed spot occupied/booked
        GPIO.output (ledPins[index], GPIO.HIGH)
        print("spot"+str(index)+" is occupied")
    if(state==False): # If state = false R-LED off & G-LED on -> indexed spot is empty/booking timeout
        GPIO.output (ledPins[index], GPIO.LOW)
        print("spot"+str(index)+" is empty")

    

while(True): // receiving data from Database
    print("test")
    data, address = receiveSocket.recvfrom(3000)   
    print(str(data))
    message = str(data).split(":")[1]
    indexToChange = message.split(",")[0]
    stateString = message.split(",")[1]
    if(stateString == "true"):
        stateToChange = False
    else:
        stateToChange = True
        
    toggleLED(int(indexToChange), stateToChange)

GPIO.cleanup()

    