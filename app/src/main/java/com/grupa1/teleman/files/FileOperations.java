package com.grupa1.teleman.files;

import android.os.Environment;

import com.grupa1.teleman.exceptions.NotImplementedException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class FileOperations {
    public static AppFile getFile(FILES.FILE_TYPE fileEnum){
        String path = "";
        switch (fileEnum){
            case CONFIG: path = FILES.configPath;       break;
            case LASTLOGIN: path = FILES.lastloginPath; break;
        }
        return new AppFile(path, fileEnum);
    }

    public static boolean checkFile(AppFile file, boolean createFile){
        if(!(file.exists())){
            if(createFile)
               createNewFile(file.file_type);
            return false;
        }
        return true;
    }

    private static boolean createNewFile(FILES.FILE_TYPE fileType) {
        if (fileType == FILES.FILE_TYPE.CONFIG) {
            try {
                FileWriter writer = new FileWriter(getFile(FILES.FILE_TYPE.CONFIG));
                writer.write(FILES.generateEmptyFileContent(FILES.FILE_TYPE.CONFIG));
            } catch (Exception ex) {
                return false;
            }
        }
        return true;
    }

    public static String[] readFile(FILES.FILE_TYPE fileType){
        switch (fileType){
            case CONFIG:{
                AppFile file = getFile(FILES.FILE_TYPE.CONFIG);
                String[] output = new String[FILES.configKeys.length];
                if (checkFile(file, false)){
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String currentLine="";
                        int index=0;
                        while((currentLine = reader.readLine()) != null){
                            output[index] = currentLine.substring(currentLine.indexOf(':')+1);
                            ++index;
                        }
                        reader.close();
                        return output;
                    } catch (Exception ex){
                        return null;
                    }
                }
                else return null;
            }
            case LASTLOGIN:{

            }
            default: return null;
        }
    }

    public static void saveToFile(FILES.FILE_TYPE fileType, String[] data){
        switch(fileType){
            case CONFIG:{
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(FILES.FILE_TYPE.CONFIG)));
                    writer.write(FILES.generateFileContent(fileType, data));
                    //for(int i=0; i<FILES.configKeys.length; i++){
                    //    writer.append(String.format("%s:%s\n", FILES.configKeys[i], data[i]));
                    //}
                    writer.close();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}
