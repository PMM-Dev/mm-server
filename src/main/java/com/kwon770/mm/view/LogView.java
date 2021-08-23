package com.kwon770.mm.view;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogView {

    public static void logInfoExceptionTitle(Exception e) {
        log.info(e.toString());
    }

    public static void logInfoExceptionString(String str) {
        log.info(str);
    }

    public static void logErrorStacktraceWithMessage(Exception e, String message) {
        log.error(message);
        e.printStackTrace();
    }
}
