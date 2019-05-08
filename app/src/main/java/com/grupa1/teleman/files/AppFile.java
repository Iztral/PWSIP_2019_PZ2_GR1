package com.grupa1.teleman.files;

import android.app.Application;

import java.io.File;
import java.net.URI;

public class AppFile extends File {
    FILES.FILE_TYPE file_type;

    public AppFile(String pathname) {
        super(pathname);
    }

    public AppFile(String parent, String child) {
        super(parent, child);
    }

    public AppFile(File parent, String child) {
        super(parent, child);
    }

    public AppFile(URI uri) {
        super(uri);
    }

    public AppFile(String pathname, FILES.FILE_TYPE type) {
        super(pathname);
        file_type = type;
    }

    public AppFile(String parent, String child, FILES.FILE_TYPE type) {
        super(parent, child);
        file_type = type;
    }

    public AppFile(File parent, String child, FILES.FILE_TYPE type) {
        super(parent, child);
        file_type = type;
    }

    public AppFile(URI uri, FILES.FILE_TYPE type) {
        super(uri);
        file_type = type;
    }
}
