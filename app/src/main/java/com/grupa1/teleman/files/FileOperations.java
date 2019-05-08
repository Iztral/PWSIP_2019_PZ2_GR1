package com.grupa1.teleman.files;

import android.os.Environment;

import com.grupa1.teleman.exceptions.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {
    public static AppFile getFile(FILES.FILE_TYPE fileEnum){
        String path = "";
        switch (fileEnum){
            case CONFIG: path = FILES.configPath;       break;
            case LASTLOGIN: path = FILES.lastloginPath; break;
        }
        return new AppFile(path, fileEnum);
    }

    public static void openFile(AppFile file){
        if(!(file.exists())){
            try{
                FileWriter writer = new FileWriter(file);
                writer.append("databaseAddress:"
                        + "databasePORT:"
                        + "databasePASSWORD:"
                        + "databaseUSERNAME:");
            } catch (IOException ioEx){
                ioEx.printStackTrace();
            }
        }

    }

    public ConfigFile readConfigFile(AppFile file){
        if(file.file_type != FILES.FILE_TYPE.CONFIG) return null;
        ConfigFile cfg = new ConfigFile(getDataAsArrayList(file));
        return cfg;
    }

    private ArrayList<String> getDataAsArrayList(AppFile file){
        ArrayList<String> dataList = new ArrayList<>();
        while(true){

            break;
        }
        return dataList;
    }
}
