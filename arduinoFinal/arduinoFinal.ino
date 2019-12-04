#include <Wire.h>
// Include the Servo Motor library
#include <Servo.h>
//Iclude the Keypad library
#include <Keypad.h>
//Iclude the LCD library
Servo servoMotor;
int IRsensor1 = 0;
int ledPin = 11; // same as mentioned below
int signalPin = 12;// need to edit based on the connection for pin code
int PIN_CodeLength = 5;
float sensorRightValue;
float sensorLeftValue;
boolean range;
int rotate;
int pin = 0;
int val =0;
const int buzzer = 10; //buzzer to arduino pin 10
boolean checkingPin = false;
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
  //pinMode(11, OUTPUT);       //setting the pin 13 in the arduino board as output pin
  servoMotor.attach(11);      // attaching the servo motor function to pin 9
  servoMotor.write(0);       // intial set up of the position of the servo motor which is now at 0 degrees
  pinKeys.begin(makeKeymap(keysPad));
  pinMode(buzzer, OUTPUT); // Set buzzer - pin 9 as an output
  pinMode(IRsensor1,INPUT);  //setting the first IR sensor as input

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
    
    if (Serial.available()>=1) {
      String response = Serial.readString();
      if(response == 1){
      buzz(true)
      open();
      delay(5000);
      close();
      }else{
      buzz(false);
      }
  }
  clearData();F
}
void clearData() {
  while (pinReceive_count != 0) {
    pinReceive[pinReceive_count--] = 0;
  }
  return;
}

  void exit(){
    
    val = digitalRead(IRsensor1);     // read the input pin
    //Serial.print(val);
    //digitalWrite(ledPin, val);    // sets the LED to the button's value
    if ( val ==1){
      open();
      delay(2000);
      close();      
    }

    
  }
void open() {
  for (rotate = 0; rotate <= 100; rotate += 1) { //rotating the motor to 90 degrees signifies the open gate
    servoMotor.write(rotate);
    delay(15);
  }
  delay(1000);
}

void buzz(bool isValid){
  
  if (isValid = true){
    tone(buzzer, 1000); // Send 1KHz sound signal...
    delay(100);        // ...for 1 sec
    noTone(buzzer);     // Stop sound...
    delay(100);        // ...for 1sec
    }
    if (isValid = false){
      tone(buzzer, 1000); // Send 1KHz sound signal...
      delay(100);        // ...for 1 sec
      //exit;
      noTone(buzzer);     // Stop sound...
    }
    else {
      noTone(buzzer);     // Stop sound...
      }
}

void close() {
  for (rotate = 100; rotate >= 1; rotate -= 5) {
    servoMotor.write(rotate);
    delay(5);
  }
  delay(1000);
}
