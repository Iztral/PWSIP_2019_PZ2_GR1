package com.grupa1.teleman.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FileOperations {
    public static AppFile getFile(FILES.FILE_TYPE fileEnum) {
        String path = "";
        switch (fileEnum) {
            case CONFIG:
                path = FILES.configPath;
                break;
            case LASTLOGIN:
                path = FILES.lastloginPath;
                break;
        }
        return new AppFile(path, fileEnum);
    }

    public static boolean checkFile(AppFile file, boolean createFile) {
        if (!(file.exists())) {
            if (createFile)
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

    public static String[] readFile(FILES.FILE_TYPE fileType) {
        switch (fileType) {
            case CONFIG: {
                AppFile file = getFile(FILES.FILE_TYPE.CONFIG);
                String[] output = new String[FILES.configKeys.length];
                if (checkFile(file, false)) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String currentLine;
                        int index = 0;
                        while ((currentLine = reader.readLine()) != null) {
                            output[index] = currentLine.substring(currentLine.indexOf(':') + 1);
                            ++index;
                        }
                        reader.close();
                        return output;
                    } catch (Exception ex) {
                        return null;
                    }
                } else return null;
            }
            case LASTLOGIN: {

            }
            default:
                return null;
        }
    }

    public static void saveToFile(FILES.FILE_TYPE fileType, String[] data) {
        switch (fileType) {
            case CONFIG: {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(FILES.FILE_TYPE.CONFIG)));
                    writer.write(FILES.generateFileContent(fileType, data));
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}
