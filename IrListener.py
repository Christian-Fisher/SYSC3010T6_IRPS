import time
import RPi.GPIO as GPIO
import socket

localIP     = "192.168.4.5"
localPort   = 20001
bufferSize  = 1024
# Create a datagram socket
UDPServerSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
# Bind to address and ip
UDPServerSocket.bind((localIP, localPort))

irPins = [2,3,4,5,6,7,8,9,10,11]
flag = []

GPIO.setmode(GPIO.BCM)

for i in range (0,9):
    GPIO.setup(irPins[i], GPIO.IN)
    
"""# will use this if above for loop fails
GPIO.setup(2,GPIO.IN) 
GPIO.setup(3,GPIO.IN)
GPIO.setup(4,GPIO.IN)
GPIO.setup(5,GPIO.IN)
GPIO.setup(6,GPIO.IN)
GPIO.setup(7,GPIO.IN)
GPIO.setup(8,GPIO.IN)
GPIO.setup(9,GPIO.IN)
GPIO.setup(10,GPIO.IN)
GPIO.setup(11,GPIO.IN)
"""
def toStr(s): # To merge array elements into string of bits
        str = ""
        for n in s:
            str += n
        return str

while True: #Bit stream or flag generation eg: = "C2C1C0B2B1B0A2A1A0E0" = "1001001110" => C2-A0: spots, E0:exit gate
    time.sleep (5)
    for j in range (0,9):
        if (GPIO.input(irPins[j]) == True): # object detected within IR range
            flag [j] = 1 
        elif (GPIO.input(irPins[j]) == False):  # No object detected within IR range 
            flag [j] = 0
        else: # neither detected nor not detected - error occured
            flag [j] = 5 
            print("An error has occured")
    print (toStr(flag)) # print flag bits
    #Below: Sending string value as UDP datagram forever
    msgFromServer       = toStr(flag)
    bytesToSend         = str.encode(msgFromServer)
    UDPServerSocket.sendto(bytesToSend, address)
finally:
    GPIO.cleanup()

