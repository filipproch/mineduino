package cz.jacktech.mineduino.entities;

import java.util.HashMap;

/**
 * Created by toor on 16.11.14.
 */
public class EntityValueStore {

    private HashMap<String, Object> mDataHashMap = new HashMap<String, Object>();

    public boolean contains(String key){
        return mDataHashMap.containsKey(key);
    }

    public void putInt(String key, int value){
        mDataHashMap.put(key, value);
    }
    public void putBoolean(String key, boolean value){
        mDataHashMap.put(key, value);
    }

    public void putString(String key, String value){
        mDataHashMap.put(key, value);
    }

    public void putObject(String key, Object value){
        mDataHashMap.put(key, value);
    }

    public int getInt(String key){
        if(contains(key))
            return (Integer)mDataHashMap.get(key);
        return 0;
    }

    public boolean getBoolean(String key){
        if(contains(key))
            return (Boolean)mDataHashMap.get(key);
        return false;
    }

    public String getString(String key){
        if(contains(key))
            return (String)mDataHashMap.get(key);
        return null;
    }

    public Object getObject(String key){
        if(contains(key))
            return mDataHashMap.get(key);
        return null;
    }

}
