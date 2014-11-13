package cz.jacktech.mineduino.serialiface;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by toor on 11.11.14.
 */
public class SerialManager {

    private static final String SERIAL_PORT = "/dev/ttyUSB0";
    private static SerialManager instance;
    private static final String TAG = SerialManager.class.getSimpleName();
    private SerialPort serialPort;
    private Thread mSerialIfaceReaderThread;

    private HashMap<Integer,ArduinoDigitalPin> digitalPins = new HashMap<Integer, ArduinoDigitalPin>();

    private SerialManager(){
    }

    public static SerialManager getInstance() {
        if(instance == null)
            instance = new SerialManager();
        return instance;
    }

    public void connect(){
        System.out.println("SerialManager connecting");
        serialPort = new SerialPort(SERIAL_PORT);
        try {
            serialPort.openPort();
            mSerialIfaceReaderThread = new Thread(serialIfaceReader);
            mSerialIfaceReaderThread.start();
            System.out.println("SerialManager connected successfully");
        } catch (SerialPortException e) {
            System.out.println("SerialManager failed");
            e.printStackTrace();
        }
    }

    public void close(){
        System.out.println("SerialManager disconnecting");
        if(isAvailable()) {
            mSerialIfaceReaderThread.interrupt();
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isAvailable(){
        return serialPort != null && serialPort.isOpened();
    }

    private Runnable serialIfaceReader = new Runnable() {

        private StringBuilder cmd = new StringBuilder();

        @Override
        public void run() {
            try {
                System.out.println("SerialManager starting reader");
                startReader();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void startReader() throws InterruptedException, SerialPortException {
            String line;
            System.out.println("SerialManager reader started");
            while(isAvailable()){
                line = serialPort.readString();
                if(line != null) {
                    cmd.append(line);
                    //System.out.println("currentData: "+cmd.toString());
                    int begin, end;
                    if((begin = cmd.indexOf("#")) >= 0 && (end = cmd.indexOf("$")) > 0){
                        String input = cmd.substring(begin, end);
                        cmd = new StringBuilder();
                        for(String dataLine : input.split("@")){
                            if(dataLine.length() > 0)
                                inputReceived(new SerialData(dataLine));
                        }
                    }
                }
            }
        }
    };

    private void inputReceived(SerialData input) {
        //System.out.println("input received: "+input.toString());
        if(!digitalPins.containsKey(input.getInt(0))){
            digitalPins.put(input.getInt(0), new ArduinoDigitalPin(input));
        }else{
            digitalPins.get(input.getInt(0)).status = input.getInt(1);
        }
    }

    public boolean getPinStatus(int pinNumber){
        if(hasPin(pinNumber)) {
            return digitalPins.get(pinNumber).status == 1;
        }
        return false;
    }

    private boolean hasPin(int pinNumber) {
        return digitalPins.containsKey(pinNumber);
    }

    public boolean enablePin(int pinNumber){
        return sendCmd("changeHigh "+pinNumber);
    }

    public boolean disablePin(int pinNumber){
        return sendCmd("changeLow "+pinNumber);
    }

    public boolean setupInput(int pinNumber){
        return sendCmd("setupInput "+pinNumber);
    }

    public boolean setupOutput(int pinNumber){
        return sendCmd("setupOutput "+pinNumber);
    }

    private boolean sendCmd(String cmd) {
        if(serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.writeString(cmd + "\r");
                System.out.println("sent command: "+cmd);
                return true;
            } catch (SerialPortException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void resetPin(int arduinoPin) {
        setupOutput(arduinoPin);
        disablePin(arduinoPin);
    }

    public boolean writeAnalog(int arduinoPin, int value) {
        return sendCmd("writeAna "+arduinoPin+" "+value);
    }
}
