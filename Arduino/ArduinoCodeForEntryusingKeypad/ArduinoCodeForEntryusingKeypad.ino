#include <Wire.h>
// Include the Servo Motor library
#include <Servo.h>
//Iclude the Keypad library
#include <Keypad.h>
//Iclude the LCD library
Servo servoMotor;
int IRsensor1 = 0;
int IRsensor2 = 0;
int ledPin = 11; // same as mentioned below
int signalPin = 12;// need to edit based on the connection for pin code
int PIN_CodeLength = 5;
float sensorRightValue;
float sensorLeftValue;
boolean range;

//Keypad

char pinReceive[4];
char pinSet[] = "1234";
byte pinReceive_count = 0;
byte pinSet_count = 0;
bool checkPin; //checks for correct pin
char entryKey; // user
const byte ROWS = 4;
const byte COLUMNS = 4;

char keysPad[ROWS][COLUMNS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

byte pins1[ROWS] = {9, 8, 7, 6};
byte pins2[COLUMNS] = {5, 4, 3, 2};

Keypad pinKeys = Keypad(makeKeymap(keysPad), pins1, pins2, ROWS, COLUMNS);


void setup() {
  Serial.begin(9600);        //Serial connection initial setup at 9600 bits of data per second
  pinMode(11, OUTPUT);       //setting the pin 13 in the arduino board as output pin
  servoMotor.attach(9);      // attaching the servo motor function to pin 9
  servoMotor.write(0);       // intial set up of the position of the servo motor which is now at 0 degrees
  pinKeys.begin(makeKeymap(keysPad));

}

void loop() {
  entry();
}
void entry() {
  char entryKey = pinKeys.getKey();
  if (entryKey) {
    Serial.print(entryKey);
    pinReceive[pinReceive_count] = entryKey;
    pinReceive_count++;
  }
  
  if (pinReceive_count == PIN_CodeLength - 1) {
    Serial.println(pinReceive);
    if (strcmp(pinReceive, pinSet)) {
    //open();
    digitalWrite(11, HIGH);
    delay(5000);
    //close();
    digitalWrite(11, LOW);
  } else {
    delay(2000);
  }
  clearData();
  }
}
void clearData() {
  while (pinReceive_count != 0) {
    pinReceive[pinReceive_count--] = 0;
  }
  return;
}
void open() {
  int rotate = 0;
  for (rotate = 0; rotate <= 90; rotate += 1) { //rotating the motor to 90 degrees signifies the open gate
    servoMotor.write(rotate);
    delay(10000);
  }
  delay(1000);
}

void close() {
  int rotate = 0;
  for (rotate = 90; rotate >= 0; rotate -= 5) {
    servoMotor.write(rotate);
    delay(10000);
  }
  delay(1000);
}
