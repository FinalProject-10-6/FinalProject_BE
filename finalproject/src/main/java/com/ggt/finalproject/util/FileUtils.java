package com.ggt.finalproject.util;


import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static boolean imageFileCheck(File file) {
        String fileName = file.getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length());
        final String[] IMAGE_EXTENSION = {"JPG", "jpg", "jpeg", "JPEG", "PNG", "png"};

        int len = IMAGE_EXTENSION.length;
        for (int i = 0; i < len; i++) {
            if (ext.equals(IMAGE_EXTENSION[i])) {
                return true; //
            }
        }
        return false;
    }
}