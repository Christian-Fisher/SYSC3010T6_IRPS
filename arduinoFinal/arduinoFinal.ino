#include <Keypad.h>
#include <Wire.h>
// Include the Servo Motor library
#include <Servo.h>
//Iclude the Keypad library
Servo servoExit, servoEntry;
int IRsensor = 13;
int PIN_CodeLength = 5;
int rotate;
int pin = HIGH;
const int buzzer = 10; //buzzer to arduino pin 10
boolean checkingPin = false;
boolean pinOK = false;

char pinReceive[4];
String correctPin = "1234";
String pinEntered = "";
byte pinReceive_count = 0;
byte pinSet_count = 0;
bool checkPin; //checks for correct pin
char entryKey; // user
const byte ROWS = 4;
const byte COLUMNS = 4;
char pinSet[] = "1234";
String inputString = "";
char keysPad[ROWS][COLUMNS] = {
  {
    '1', '2', '3', 'A'
  }
  ,
  {
    '4', '5', '6', 'B'
  }
  ,
  {
    '7', '8', '9', 'C'
  }
  ,
  {
    '*', '0', '#', 'D'
  }
};

byte pins1[ROWS] = {
  9, 8, 7, 6
};
byte pins2[COLUMNS] = {
  5, 4, 3, 2
};

Keypad pinKeys = Keypad(makeKeymap(keysPad), pins1, pins2, ROWS, COLUMNS);

void setup() {
  Serial.begin(9600);        //Serial connection initial setup at 9600 bits of data per second
  //pinMode(11, OUTPUT);       //setting the pin 13 in the arduino board as output pin
  servoEntry.attach(12);      // attaching the servo motor function to pin 9
  servoEntry.write(100);       // intial set up of the position of the servo motor which is now at 0 degrees
  servoExit.attach(11);      // attaching the servo motor function to pin 9
  servoExit.write(0);
  pinKeys.begin(makeKeymap(keysPad));
  pinMode(buzzer, OUTPUT); // Set buzzer - pin 10 as an output
  //pinMode(ledPin, OUTPUT); // Set buzzer - pin 12 as an output
  pinMode(IRsensor, INPUT); //setting the first IR sensor as input

}

// Detects the car with the help of IR sensor and allows the car to exit
void exitGate() {
  pin = digitalRead(IRsensor);
  if (pin == LOW) {
    for (rotate = 0; rotate <= 100; rotate += 1) { //rotating the motor to 100 degrees to open gate
      servoExit.write(rotate);
      delay(15);
    }
    delay(5000);
    for (rotate = 100; rotate >= 1; rotate -= 1) { // closing gate after a delay
      servoExit.write(rotate);
      delay(15);
    }
    delay(1000);
  }
}

// The arduino keeps running until disconnected
void loop() {
  entry();
  exitGate();

}

// Clears all the old data saved in the arduino
void clearData() {
  while (pinReceive_count != 0) {
    pinReceive[pinReceive_count--] = 0;
  }
  return;
}

// Takes a boolean parameter and the buzzer responds accordingly
void buzz(boolean isValid) {

  if (isValid == false) {
    tone(buzzer, 100); // Send 1KHz sound signal...
    delay(1000);
    noTone(buzzer);     // Stop sound...
    //delay(100);        // ...for 1sec
  }
  else if (isValid == true) {
    tone(buzzer, 100); // Send 1KHz sound signal...
    delay(100);        // ...for 1 sec
    noTone(buzzer);    // Stop sound...
    tone(buzzer, 100);
    noTone(buzzer);
  }
  else {
    noTone(buzzer);     // Stop sound...
  }
}

//Allows the car to enter if the entered PIN is correct taking the pinOK as a boolean input
void entryGate(boolean pinOK) {
  if (pinOK == true) {
    for (rotate = 100; rotate >= 0; rotate -= 1) { //rotating the motor to 100 degrees to open gate
      servoEntry.write(rotate);
      delay(15);
    }
    delay(5000);
    for (rotate = 0; rotate <= 100; rotate += 1) { // close gate after a delay
      servoEntry.write(rotate);
      delay(15);
    }
  }

}

//This method was used for testing the connection
void entry() {

  char entryKey = pinKeys.getKey();
  if (entryKey) {
    pinReceive[pinReceive_count] = entryKey;
    pinReceive_count++;
  }

  if (pinReceive_count == PIN_CodeLength - 1) {
    //    for (int i = 0; i < 4; i++) {
    //      byte lengthArray[pinReceive[i]] = {0,0,0,0,0,0,0,0,0};
    //          Serial.println((sizeof(lengthArray) / sizeof(lengthArray[0])));
    //          delay(100);
    //    }
    //    byte LengthArray[10] = {0,0,0,0,0,0,0,0,0,0};
    //    Serial.println((sizeof(LengthArray) / sizeof(LengthArray[0])));

//
//    while (!Serial.available()) {}
//    while (Serial.available()) {
//      delay(3);
//      char character = Serial.read();
//      inputString += character;
//    }
    inputString.trim();
    if (strcmp(pinReceive, pinSet)) {
      buzz(true);
      delay(1000);
      buzz('1');
      entryGate(true);
    }
    else {
      buzz(false);
    }
    clearData();
  }


}
