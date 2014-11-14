#include <Arduino.h>
#include <OneWire.h>
#include <DallasTemperature.h>
void setup();
void loop();
void serialEvent();
void readCommand();
String nextSegmentString();
int nextSegmentInt();
void printUno();
void setupInput();
void setupOutput();
void setupDallasTemp();
void writeAnalog();
void updatePinHigh();
void updatePinLow();
void setupUno();
void setupMega2560();
#line 1 "src/mineduino.ino"
//#include <OneWire.h>
//#include <DallasTemperature.h>

OneWire oneWire = null;//(8)
DallasTemperature temps = null;//(&oneWire)

String currentCommand = "";
boolean commandReceived = false;

void setup(){
  Serial.begin(9600);
  currentCommand.reserve(100);

  setupUno();
}

void loop(){
  printUno();
  delay(500);
}

void serialEvent(){
  while(Serial.available()){
    char inChar = (char) Serial.read();
    currentCommand += inChar;
    if(inChar == '\n'){
      commandReceived = true;
      currentCommand = currentCommand.substring(1);
      readCommand();
    }
  }
}

void readCommand(){
  if(commandReceived){
    boolean used = true;
    String line = nextSegmentString();

    commandReceived = false;
    currentCommand = "";
  }
}

String nextSegmentString(){
  int delimiterIndex = currentCommand.indexOf(CMD_DELIMITER);
  String line = currentCommand.substring(0, delimiterIndex);
  currentCommand = currentCommand.substring(delimiterIndex+1);
  return line;
}

int nextSegmentInt(){
  return nextSegmentString().toInt();
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
  int pinNumber = nextSegmentInt();
  pinMode(pinNumber, INPUT);
}

void setupOutput(){
  int pinNumber = nextSegmentInt();
  pinMode(pinNumber, OUTPUT);
}

void setupDallasTemp(){
  int pinNumber = nextSegmentInt();
  oneWire = new OneWire(pinNumber);
  temps = new DallasTemperature(&oneWire);
}

void writeAnalog(){
  int pinNumber = nextSegmentInt();
  int fadeValue = nextSegmentInt();
  analogWrite(pinNumber, fadeValue);  
}

void updatePinHigh(){
  int pinNumber = nextSegmentInt();
  digitalWrite(pinNumber, HIGH);
}

void updatePinLow(){
  int pinNumber = nextSegmentInt();
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
