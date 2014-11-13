package cz.jacktech.mineduino.serialiface;

/**
 * Created by toor on 12.11.14.
 */
public class SerialData {

    private String[] mDataArray;

    public SerialData(String received){
        parse(received);
    }

    private void parse(String received) {
        mDataArray = received.replace("#", "").replace("$", "").split(";");
    }

    public String get(int position){
        return mDataArray[position];
    }

    public int getInt(int position){
        try{
            return Integer.parseInt(mDataArray[position]);
        }catch (Exception e){}
        return 0;
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
