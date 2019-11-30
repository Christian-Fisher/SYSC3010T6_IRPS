import time
import RPi.GPIO as GPIO
import socket

ledPins = [12,13,14,15,16,17,18,19,20]
ledFlag = [True]
GPIO.setmode(GPIO.BCM)

for i in range (0,8):
    GPIO.setup(ledPins[i], GPIO.OUT)
"""
GPIO.setup(12,GPIO.OUT)
GPIO.setup(13,GPIO.OUT)
GPIO.setup(14,GPIO.OUT)
GPIO.setup(15,GPIO.OUT)
GPIO.setup(16,GPIO.OUT)
GPIO.setup(17,GPIO.OUT)
GPIO.setup(18,GPIO.OUT)
GPIO.setup(19,GPIO.OUT)
GPIO.setup(20,GPIO.OUT)
"""

#Below: Receiving string value as UDP datagram forever

while True:
  
    time.sleep (1)
    ledFlag = list(data) # Creating a arraylist of char from data
    for t in range (0,8):
        if (ledFlag[t] == '1'):
            GPIO.output (ledPins[t], GPIO.HIGH)
        elif (ledFlag[t] == '0'):
            GPIO.output (ledPins[t], GPIO.LOW)
        else: # Received data incombatible
            print("Received flag is not recognized")

GPIO.cleanup()
    
    
            


