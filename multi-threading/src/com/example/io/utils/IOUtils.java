package com.example.io.utils;

import java.io.*;

public class IOUtils {
    public static void copy(InputStream src, OutputStream dest) throws Exception {
        int value;

        while((value = src.read()) != -1) {
            dest.write(value);
        }
    }

    public static void copyFile(String sourceFile, String destinationFile) throws Exception{

        FileInputStream fis = new FileInputStream(sourceFile);
        FileOutputStream fos = new FileOutputStream(destinationFile);
        IOUtils.copy(fis, fos);
        fis.close();
        fos.close();
    }
}
