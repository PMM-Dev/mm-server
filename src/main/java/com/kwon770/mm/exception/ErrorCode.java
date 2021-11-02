package com.kwon770.mm.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "PROBLEM OCCURRED FROM SERVER"),
    AUTHENTICATION_FAILED(401, "AUTHENTICATION FAILED"),
    LOGIN_FAILED(401, "LOGIN FAILED"),
    INVALID_JWT_TOKEN(401, "INVALID JWT TOKEN"),
    EXPIRED_JWT_TOKEN(401, "EXPIRED TOKEN"),
    ILLEGAL_ARGUMENT(422, "ILLEGAL ARGUMENT");

    public static String NO_MEMBER_BY_USERID = "MemberId과 일치하는 Member가 없음 : MemberId = ";
    public static String NO_MEMBER_BY_EMAIL = "email과 일치하는 Member가 없음 : email = ";
    public static String NO_RESTAURANT_BY_RESTAURANTID = "restaurantId와 일치하는 Restaurant이 없습니다. restaurantId = ";
    public static String NO_RESTAURANT_BY_RESTAURANTNAME = "restaurantName와 일치하는 Restaurant이 없습니다. restaurantName = ";
    public static String NO_REVIEW_BY_RESTAURANTID = "Restaurant에 작성된 Review가 없음 : RestaurantId = ";
    public static String NO_REPORT_BY_REPORTID = "reportId와 일치하는 Report가 없음 : reportId = ";
    public static String NOT_REPORT_OWNER = "Report의 소유자가 아닙니다";

    public static String NO_TITLE_MESSAGE = "해당 title 존재하지 않음 : ";
    public static String NO_THEME_MESSAGE = "해당 theme 존재하지 않음 : ";
    public static String NO_SPECIAL_MESSAGE = "해당 special 존재하지 않음 : ";

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
