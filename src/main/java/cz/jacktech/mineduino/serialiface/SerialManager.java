package cz.jacktech.mineduino.serialiface;

import cz.jacktech.mineduino.MineDuinoMod;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoDevice;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoDigitalPin;
import cz.jacktech.mineduino.serialiface.arduino.ArduinoPin;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toor on 11.11.14.
 */
public class SerialManager {

    private static String SERIAL_PORT = "/dev/ttyUSB0";
    private static SerialManager instance;
    private static final String TAG = SerialManager.class.getSimpleName();
    private SerialPort serialPort;
    private Thread mSerialIfaceReaderThread;

    private List<ArduinoDevice> arduinoDevices = new ArrayList<ArduinoDevice>();
    private List<ArduinoPin> arduinoPins = new ArrayList<ArduinoPin>();

    private SerialManager(){
        SERIAL_PORT = MineDuinoMod.instance.config.getSerialPort();
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
                cmd.append(serialPort.readString());
                if(cmd.length() > 0) {
                    parseCmd();
                    /*cmd.append(line);
                    //System.out.println("currentData: "+cmd.toString());
                    int begin, end;
                    if((begin = cmd.indexOf("#")) >= 0 && (end = cmd.indexOf("$")) > 0){
                        String input = cmd.substring(begin, end);
                        cmd = new StringBuilder();
                        for(String dataLine : input.split("@")){
                            if(dataLine.length() > 0)
                                inputReceived(new SerialData(dataLine));
                        }
                    }*/
                }
            }
        }

        private void parseCmd() {
            if(cmd.indexOf("\n") != -1){
                String[] input = cmd.toString().split("\n");
                cmd = new StringBuilder(input[input.length-1]);
                for(int i = 0;i<input.length-1;i++){
                    String line = input[i];
                    inputReceived(new SerialData(line));
                }
            }
        }
    };

    private void inputReceived(SerialData input) {
        System.out.println("input received: "+input.toString());

        /*if(!digitalPins.containsKey(input.getInt(0))){
            digitalPins.put(input.getInt(0), new ArduinoDigitalPin(input));
        }else{
            digitalPins.get(input.getInt(0)).status = input.getInt(1);
        }*/
    }

    private boolean hasPin(int pinNumber) {
        for(ArduinoPin pin : arduinoPins)
            if(pin.pinNumber == pinNumber)
                return true;
        return false;
    }

    /*public boolean enablePin(int pinNumber){
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
    }*/

    private boolean sendCmd(String cmd) {
        if(serialPort != null && serialPort.isOpened()) {
            try {
                serialPort.writeString(cmd + "\n");
                System.out.println("sent command: " + cmd);
                return true;
            } catch (SerialPortException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void resetPin(ArduinoPin arduinoPin) {
        sendCmd("/set/null/"+arduinoPin.pinNumber);
    }

    public void writePin(ArduinoDigitalPin pin, int value) throws IncorrectPinModeException {
        if(pin.pinType == ArduinoPin.PinMode.OUTPUT)
            sendCmd("/write/1/"+pin.pinNumber+"/"+value);
        else if(pin.pinType == ArduinoPin.PinMode.ANALOG_OUTPUT)
            sendCmd("/write/0/"+pin.pinNumber+"/"+value);
        else
            throw new IncorrectPinModeException();
    }

    public void setupPin(ArduinoPin pin, ArduinoPin.PinMode pinMode) {
        switch (pinMode){
            case INPUT:
                sendCmd("/set/in/"+pin.pinNumber);
                break;
            case OUTPUT:
                sendCmd("/set/out/"+pin.pinNumber);
                break;
            case ANALOG_INPUT:
                sendCmd("/set/ain/"+pin.pinNumber);
                break;
            case ANALOG_OUTPUT:
                sendCmd("/set/aout/"+pin.pinNumber);
                break;
        }
    }
}
