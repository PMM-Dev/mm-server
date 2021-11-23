package com.kwon770.mm.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_AUTHOR(400),
    AUTHENTICATION_FAILED(401),
    LOGIN_FAILED(401),
    INVALID_JWT_TOKEN(401),
    EXPIRED_JWT_TOKEN(401),
    ILLEGAL_ARGUMENT(422),
    INTERNAL_SERVER_ERROR(500),
    SYSTEM_IO_ERROR(500),
    IMAGE_IO_ERROR(500);

    public static String SOCIAL_TOKEN_TYPE_NOT_EXIST = "socialTokenType 필드가 null";
    public static String NO_USER_BY_GOOGLE_SOCIAL_TOKEN = "해당 Google SocialToken과 일치하는 Member가 없음";
    public static String WRONG_GOOGLE_SOCIAL_TOKEN = "올바르지 않은 Google SocialToken";
    public static String NO_USER_BY_APPLE_SOCIAL_TOKEN = "해당 Apple SocialToken과 일치하는 Member가 없음";
    public static String WRONG_APPLE_SOCIAL_TOKEN = "올바르지 않은 Apple SocialToken";

    public static String NOT_AUTHOR_MESSAGE = "이 요청을 보낼 수 있는 소유자가 아님 : currentMemberId = ";
    // ?
    public static String NOT_REPORT_OWNER = "Report의 소유자가 아닙니다";

    public static String NO_MEMBER_BY_USERID = "MemberId과 일치하는 Member가 없음 : MemberId = ";
    public static String NO_MEMBER_BY_EMAIL = "email과 일치하는 Member가 없음 : email = ";
    public static String NO_RESTAURANT_BY_RESTAURANTID = "restaurantId와 일치하는 Restaurant이 없습니다. restaurantId = ";
    public static String NO_RESTAURANT_BY_RESTAURANTNAME = "restaurantName와 일치하는 Restaurant이 없습니다. restaurantName = ";
    public static String NO_REVIEW_BY_RESTAURANTID = "Restaurant에 작성된 Review가 없음 : RestaurantId = ";
    public static String NO_REVIEW_BY_REVIEWID = "ReviewId와 일치하는 Review가 없습니다. ReviewId = ";
    public static String NO_IMAGE_BY_RESTAURANTID = "Restaurant에 업로드된 사진이 없음 : restaurantId = ";
    public static String NO_REPORT_BY_REPORTID = "reportId와 일치하는 Report가 없음 : reportId = ";
    public static String NO_POST_BY_POSTID = "postId와 일치하는 Post가 없음 : postId = ";
    public static String NO_COMMENT_BY_COMMENTID = "commentId와 일치하는 Comment가 없음 : commentId = ";

    public static String NO_TITLE_MESSAGE = "해당 title 존재하지 않음 : ";
    public static String NO_THEME_MESSAGE = "해당 theme 존재하지 않음 : ";
    public static String NO_SPECIAL_MESSAGE = "해당 special 존재하지 않음 : ";

    public static String SYSTEM_IO_ERROR_MESSAGE = "서버의 IO 과정에서 문제 발생";
    public static String IMAGE_IO_ERROR_MESSAGE = "서버에서 이미지 IO 과정에서 문제 발생 :";
    public static String NOT_FOUND_IMAGE = "해당하는 이미지가 없음";
    public static String NOT_FOUND_IMAGE_BY_INDEX = "해당 index에 해당하는 이미지가 없음 : index = ";

    private final int status;

    ErrorCode(int status) {
        this.status = status;
    }
}
