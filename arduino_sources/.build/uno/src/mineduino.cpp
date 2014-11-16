#include <Arduino.h>
#include <OneWire.h>
#include <DallasTemperature.h>
void setup();
void loop();
void serialEvent();
void readCommand();
void setupPin(int pin, int type);
void setupAPin(int pin);
void printData();
void printAnalog(int pin);
void printDigital(int pin);
void writeAnalog(int pin, int value);
void writeDigital(int pin, int value);
void printSupportedPins();
bool isPwmPin(int pin);
String nextSegmentString();
int nextSegmentInt();
void printDallasAddress(DeviceAddress deviceAddress);
void setupPins();
void setupUno();
void setupMega2560();
#line 1 "src/mineduino.ino"
//#include <OneWire.h>
//#include <DallasTemperature.h>

#define TEMPERATURE_PRECISION 4

#define UNO 0
#define MEGA2560 1

#define NOTHING 0
#define D_IN_PIN 1
#define D_OUT_PIN 2
#define OW_PIN 3
#define A_OUT_PIN 4

#define ENABLED 1
#define DISABLED 0

#define ANALOG 0
#define DIGITAL 1

#if defined(__AVR_ATmega2560__) //mega 2560 (atmega 2560)
#define USED_ARDUINO MEGA2560
#define PINS_NUMBER 54
#define ANALOG_PINS 6
#endif
#if defined(__AVR_ATmega168__) || defined(__AVR_ATmega168P__) || defined(__AVR_ATmega328P__) //uno, nano, mini pro, etc (atmega 328)
#define USED_ARDUINO UNO
#define PINS_NUMBER 14
#define ANALOG_PINS 16
#endif

const char CMD_DELIMITER = '/';

String currentCommand = "";
boolean commandReceived = false;

int * pinsStatus = new int[PINS_NUMBER];
int * analogPinsStatus = new int[ANALOG_PINS];

void setup(){
  Serial.begin(9600);
  currentCommand.reserve(100);
  printSupportedPins();
  setupPins();
}

void loop(){
  //Serial.println("/ping/");
  printData();
  delay(100);
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

    if(line == "set"){
        String what = nextSegmentString();
        if(what == "in"){
            setupPin(nextSegmentInt(), D_IN_PIN);
        }else if(what == "out"){
            setupPin(nextSegmentInt(), D_OUT_PIN);
        }else if(what == "aout"){
            setupPin(nextSegmentInt(), A_OUT_PIN);
        }else if(what == "ain"){
            setupAPin(nextSegmentInt());
        }else if(what == "null"){
            setupPin(nextSegmentInt(), NOTHING);
        }
    }else if(line == "write"){
        int what = nextSegmentInt();
        switch(what){
            case ANALOG:
                writeAnalog(nextSegmentInt(), nextSegmentInt());
                break;
            case DIGITAL:
                writeDigital(nextSegmentInt(), nextSegmentInt());
                break;
        }
    }else if(line == "connected"){
        printSupportedPins();
        setupPins();
    }else{
        Serial.println("/unknown/");
    }

    commandReceived = false;
    currentCommand = "";
  }
}

void setupPin(int pin, int type){
    if(pin >= 0 && pin <= PINS_NUMBER && pinsStatus[pin] == NOTHING){
        pinsStatus[pin] = type;
    }
}

void setupAPin(int pin){
    if(pin >= 0 && pin <= ANALOG_PINS){
        analogPinsStatus[pin] = analogPinsStatus[pin] == ENABLED ? DISABLED : ENABLED;
    }
}

void printData(){
    for(int i = 0;i < PINS_NUMBER;i++){
        if(pinsStatus[i] == D_IN_PIN){
            printDigital(i);
        }
    }

    for(int i = 0;i < ANALOG_PINS;i++){
        if(analogPinsStatus[i] == ENABLED){
            printAnalog(i);
        }
    }
}

void printAnalog(int pin){
    Serial.print("/dat/a");
    Serial.print(pin);
    Serial.print("/");
    Serial.println(analogRead(pin));
}

void printDigital(int pin){
    Serial.print("/dat/d");
    Serial.print(pin);
    Serial.print("/");
    Serial.println(digitalRead(pin));
}

void writeAnalog(int pin, int value){
    if(isPwmPin(pin) && pinsStatus[pin] == A_OUT_PIN){
        analogWrite(pin, value);
    }
}

void writeDigital(int pin, int value){
    if(pinsStatus[pin] == D_OUT_PIN){
        digitalWrite(pin, value);
    }
}

void printSupportedPins(){
    Serial.print("/setup/dpins/");
    Serial.println(PINS_NUMBER);
    Serial.print("/setup/apins/");
    Serial.println(ANALOG_PINS);
}

bool isPwmPin(int pin){
    if(USED_ARDUINO == UNO){
        switch(pin){
            case 3:
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
                return true;
        }
    }else if(USED_ARDUINO == MEGA2560){
        if((pin >= 2 && pin <= 13) || (pin >= 44 && pin <= 46)){
            return true;
        }
    }
    return false;
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

/*void setupDallasTemp(){
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

/*void dallasTemperature(){
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
}*/

void printDallasAddress(DeviceAddress deviceAddress){
  for (uint8_t i = 0; i < 8; i++){
    if (deviceAddress[i] < 16) Serial.print("0");
    Serial.print(deviceAddress[i], HEX);
  }
}

void setupPins(){
    pinsStatus = new int[PINS_NUMBER];
    analogPinsStatus = new int[ANALOG_PINS];
    if(USED_ARDUINO == UNO){
        setupUno();
    }else if(USED_ARDUINO == MEGA2560){
        setupMega2560();
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
