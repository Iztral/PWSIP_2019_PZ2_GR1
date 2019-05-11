package com.grupa1.teleman.files;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfigFile implements Parcelable {
    protected ConfigFile(Parcel in) {
        database = (String[]) in.readArray(getClass().getClassLoader());
        valid = in.readByte() != 0;
    }

    public static final Creator<ConfigFile> CREATOR = new Creator<ConfigFile>() {
        @Override
        public ConfigFile createFromParcel(Parcel in) {
            return new ConfigFile(in);
        }

        @Override
        public ConfigFile[] newArray(int size) {
            return new ConfigFile[size];
        }
    };

    public String getDatabaseAddress() {
        return database[0];
    }
    public void setDatabaseAddress(String databaseAddress) {
        this.database[0] = databaseAddress;
    }
    public String getDatabasePort() {
        return database[1];
    }
    public void setDatabasePort(String databasePort) {
        this.database[1] = databasePort;
    }
    public String getDatabasePassword() {
        return database[2];
    }
    public void setDatabasePassword(String databasePassword) {
        this.database[2] = databasePassword;
    }
    public String getDatabaseName() {
        return database[3];
    }
    public void setDatabaseName(String databaseName) {
        this.database[3] = databaseName;
    }
    public String getDatabaseUsername() {
        return database[4];
    }
    public void setDatabaseUsername(String databaseUsername) {
        this.database[4] = databaseUsername;
    }
    public String[] getDatabase() { return database; }
    public boolean isValid() {
        return valid;
    }

    public ConfigFile(String databaseAddress, String databasePort, String databasePassword, String databaseName, String databaseUsername) {
        this.database[0] = databaseAddress;
        this.database[1] = databasePort;
        this.database[2] = databasePassword;
        this.database[3] = databaseName;
        this.database[4] = databaseUsername;
        valid=true;
    }
    private String[] database;

    private boolean valid = false;

    public ConfigFile(String[] data) {
        if(data!=null) {
            database = data;
            valid=true;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(database);
    }
}
