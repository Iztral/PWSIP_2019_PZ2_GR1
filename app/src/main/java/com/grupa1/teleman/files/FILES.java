package com.grupa1.teleman.files;

import android.app.Application;
import android.content.Context;
import android.system.Os;

import com.grupa1.teleman.MainActivity;

public class FILES {
    public enum FILE_TYPE {
        CONFIG, LASTLOGIN
    }
    static final String configPath = MainActivity.currentDir + "/config.txt";
    static final String lastloginPath = MainActivity.currentDir + "/lastlogin.txt";
    static final String[] configKeys = new String[] {"databaseAddress", "databasePORT", "databasePASSWORD", "databaseNAME", "databaseUSERNAME"};
    static final String[] lastLoginKeys = new String[] {"login", "password"};
    public static String generateEmptyFileContent(FILE_TYPE type){
        String output = "";
        switch (type){
            case CONFIG:{
                for(int i=0; i<configKeys.length; i++){
                    output += configKeys[i]+":\n";
                }
                break;
            }
            case LASTLOGIN: {
                for(int i=0; i<lastLoginKeys.length; i++){
                    output += lastLoginKeys[i]+":\n";
                }
                break;
            }
        }
        return output;
    }

    public static String generateFileContent(FILE_TYPE type, String[] args){
        String output = "";
        switch (type){
            case CONFIG:{
                for(int i=0; i<configKeys.length; i++){
                    output += configKeys[i]+":" + args[i] + "\n";
                }
                break;
            }
            case LASTLOGIN: {
                for(int i=0; i<lastLoginKeys.length; i++){
                    output += lastLoginKeys[i]+":" + args[i] + "\n";
                }
                break;
            }
        }
        return output;
    }
}
