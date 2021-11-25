package com.kwon770.mm.view;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogView {

    public static void logInfoExceptionString(String str) {
        log.info(str);
    }

    public static void logErrorStacktraceWithMessage(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
    }
}
