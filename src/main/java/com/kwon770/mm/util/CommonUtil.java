package com.kwon770.mm.util;

import com.kwon770.mm.exception.SystemIOException;

import java.io.File;
import java.lang.reflect.Executable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CommonUtil {

    public static String convertLocalDateTimeToFormatString(LocalDateTime localDateTime) {
        Date date = java.sql.Timestamp.valueOf(localDateTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return format.format(date);
    }

    public static void removeImageFromServer(String filePath) {
        File file = new File(filePath);
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new SystemIOException(e);
        }
    }
}
