package com.grupa1.teleman.files;

import java.util.ArrayList;

public class ConfigFile {
    public String getDatabaseAddress() {
        return databaseAddress;
    }
    public void setDatabaseAddress(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }
    public String getDatabasePassword() {
        return databasePassword;
    }
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
    public String getDatabaseName() {
        return databaseName;
    }
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    public String getDatabasePort() {
        return databasePort;
    }
    public void setDatabasePort(String databasePort) {
        this.databasePort = databasePort;
    }

    private String databaseAddress;
    private String databasePort;
    private String databasePassword;
    private String databaseName;

    public ConfigFile(ArrayList<String> dataList){

    }
}
