package com.statanalysisapi.constant;

public final class Constants {
    // Rest Error Messages
    public static final String REST_BAD_REQUEST = "400: Bad Request";
    public static final String REST_UNAUTHORIZED = "401: Unauthorized";
    public static final String REST_FORBIDDEN = "403: Forbidden";
    public static final String REST_NOT_FOUND = "404: Not Found";
    public static final String REST_CONFLICT = "409: Conflict";
    public static final String REST_MEDIA_TYPE_NOT_SUPPORTED = "415: Media Type Not Supported";
    public static final String REST_INTERNAL_SERVER_ERROR = "500: Internal Server Error";
    public static final String REST_SERVICE_UNAVAILABLE = "503: Service Unavailable";

    // Detailed Error Messages
    public static final String INVALID_JSON = "Invalid JSON sent in request.";
    public static final String NO_VALUES_PROVIDED = "No values were provided.";
    public static final String NO_VALID_INTEGERS_PROVIDED = "No valid positive integers were provided.";
    public static final String ONLY_POSITIVE_INTEGERS_ALLOWED = "Only positive numbers are allowed!";
    public static final String NA = "n/a";
}
