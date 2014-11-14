#include <OneWire.h>
#include <DallasTemperature.h>

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

    if(line == "set"

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
  OneWire oneWire(pinNumber);
  DallasTemperature temps(&oneWire);
  temps.begin();
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

void dallasTemperature(){
  temps.requestTemperatures();
  int deviceCount = temps.getDeviceCount();
  for(int i = 0;i<deviceCount;i++){
    DeviceAddress dAddr;
    temps.getAddress(dAddr, i);
    temps.setResolution(dAddr, TEMPERATURE_PRECISION);
    Serial.print("/data/dallas/");
    printDallasAddress(dAddr);
    Serial.print("/");
    Serial.println(temps.getTempC(dAddr));
  }
}

void printDallasAddress(DeviceAddress deviceAddress){
  for (uint8_t i = 0; i < 8; i++){
    if (deviceAddress[i] < 16) Serial.print("0");
    Serial.print(deviceAddress[i], HEX);
  }
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
