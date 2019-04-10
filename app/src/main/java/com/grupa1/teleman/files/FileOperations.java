package com.grupa1.teleman.files;

import android.os.Environment;

import com.grupa1.teleman.exceptions.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperations {
    public static File getFile(FILES.FILE_TYPE fileEnum){
        String path = "";
        switch (fileEnum){
            case CONFIG: path = FILES.configPath;       break;
            case LASTLOGIN: path = FILES.lastloginPath; break;
        }
        return new File(path);
    }

    public static void openFile(File file, FILES.FILE_TYPE fileEnum){
        if(!(file.exists())){
            try{
                FileWriter writer = new FileWriter(file);
                writer.append("databaseIP:\n\r"
                        + "databasePORT:\n\r"
                        + "databaseUSERNAME:");
            } catch (IOException ioEx){
                ioEx.printStackTrace();
            }
        }

    }
}
