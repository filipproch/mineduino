package cz.jacktech.mineduino.serialiface;

import cz.jacktech.mineduino.core.MineduinoLogger;

/**
 * Created by toor on 12.11.14.
 */
public class SerialData {

    private String[] mDataArray;

    public SerialData(String received){
        parse(received);
    }

    private void parse(String received) {
        String replaced = received.replace("com:","").replace("\n","");
        mDataArray = replaced.split("/");
        MineduinoLogger.info("rec: " + received + ", rep: " + replaced + ", 0:" + mDataArray[0]);
    }

    public String get(int position){
        return mDataArray[position];
    }

    public int getInt(int position){
        return Integer.parseInt(mDataArray[position]);
    }

    public float getFloat(int position){
        return Float.parseFloat(mDataArray[position]);
    }

    public int getParts(){
        return mDataArray.length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(String s : mDataArray)
            builder.append(s).append(";");
        return builder.toString();
    }
}
