#include <Arduino.h>
#include <SoftwareSerial.h> 
#include <SerialCommand.h>
void setup();
void loop();
void printUno();
void setupInput();
void setupOutput();
void writeAnalog();
void updatePinHigh();
void updatePinLow();
void setupUno();
void setupMega2560();
#line 1 "src/mineduino.ino"
//#include <SoftwareSerial.h> 
//#include <SerialCommand.h>

SerialCommand SCmd;

void setup(){
  Serial.begin(9600); 
  SCmd.addCommand("changeHigh",updatePinHigh);
  SCmd.addCommand("changeLow",updatePinLow);
  SCmd.addCommand("writeAna",writeAnalog);
  SCmd.addCommand("setupOutput",setupOutput);
  SCmd.addCommand("setupInput",setupInput);
  setupUno();
}

void loop(){
  printUno();
  SCmd.readSerial(); 
  delay(500);
}

void printUno(){
  Serial.print("#");
  for(int i = 2;i<=13;i++){
    Serial.print(i);
    Serial.print(";");
    Serial.print(digitalRead(i));
    Serial.print("@");
  }
  Serial.println("$");
}

void setupInput(){
  int pinNumber = atoi(SCmd.next());
  pinMode(pinNumber, INPUT);
}

void setupOutput(){
  int pinNumber = atoi(SCmd.next());
  pinMode(pinNumber, OUTPUT);
}

void writeAnalog(){
  int pinNumber = atoi(SCmd.next());
  int fadeValue = atoi(SCmd.next());
  analogWrite(pinNumber, fadeValue);  
}

void updatePinHigh(){
  int pinNumber = atoi(SCmd.next());
  digitalWrite(pinNumber, HIGH);
}

void updatePinLow(){
  int pinNumber = atoi(SCmd.next());
  digitalWrite(pinNumber, LOW);
}

void setupUno(){
  for(int i = 2;i<=13;i++){
    pinMode(i, OUTPUT);
    digitalWrite(i, LOW);
  }
}

void setupMega2560(){
  for(int i = 2;i<=53;i++){
    pinMode(i, OUTPUT);
    digitalWrite(i, LOW);
  }
}
