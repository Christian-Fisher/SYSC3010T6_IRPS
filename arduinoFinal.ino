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

char keysPad[ROWS][COLUMNS] = {
  {
    '1', '2', '3', 'A'  }
  ,
  {
    '4', '5', '6', 'B'  }
  ,
  {
    '7', '8', '9', 'C'  }
  ,
  {
    '*', '0', '#', 'D'  }
};

byte pins1[ROWS] = {
  9, 8, 7, 6};
byte pins2[COLUMNS] = {
  5, 4, 3, 2};

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
  pinMode(IRsensor,INPUT);  //setting the first IR sensor as input

}

void loop() {
  entry();
  //buzz(false);
  //buzz(true);
  exitGate();

}


void buzz(boolean isValid){

  if (isValid == false){
    tone(buzzer, 2000); // Send 1KHz sound signal...
    delay(1000);  
    noTone(buzzer);     // Stop sound...
    //delay(100);        // ...for 1sec
  }
  if (isValid == true){
    tone(buzzer, 4000); // Send 1KHz sound signal...
    delay(100);        // ...for 1 sec
    noTone(buzzer);    // Stop sound...
    tone(buzzer, 4000);

  }
    else {
    noTone(buzzer);     // Stop sound...
  }
}


void entry() {
  char entryKey = pinKeys.getKey();
  if (entryKey) {
    pinReceive[pinReceive_count] = entryKey;
    pinReceive_count++;
  }

  if (pinReceive_count == PIN_CodeLength - 1) {
    Serial.println(pinReceive);

    //if (Serial.available()>=1) {
//      String response = Serial.readString();
      for(int i =0;i<=4; i++){
      pinEntered += pinReceive[i];
      }
      if(strcmp(pinReceive,pinSet)){    //response.equals("1")
        buzz(true);
        delay(1000);
        buzz('1');
        entryGate(true);
        delay(1000);
        }
      else{
         buzz(false);
        entryGate(false);
        delay(2000);
        buzz('1');
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

void exitGate(){
  pin = digitalRead(IRsensor);
  if (pin == LOW){
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
    
    else{
    }
  }
     

void entryGate(boolean pinOK) {
  if (pinOK==false){
    for (rotate = 100; rotate >= 0; rotate -= 1) { //rotating the motor to 100 degrees to open gate
    servoEntry.write(rotate);
    delay(15);
    }
    delay(5000);
    for (rotate = 0; rotate <= 100; rotate += 1) { // close gate after a delay
    servoEntry.write(rotate);
    delay(15);
    }
    delay(5000);
    }
    else{
    }
      
}




