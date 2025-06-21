package com.foodease.myapp.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1002, "User not existed", HttpStatus.NOT_FOUND),
    USER_EXISTED(1003, "User existed", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1004, "Email already exists", HttpStatus.BAD_REQUEST),
    CITY_NOT_FOUND(1005, "City not found", HttpStatus.NOT_FOUND),
    RESTAURANT_NOT_FOUND(1006, "Restaurant not found", HttpStatus.NOT_FOUND),
    MENU_ITEM_NOT_FOUND(1007, "Menu item not found", HttpStatus.NOT_FOUND),
    INVALID_FAVORITE_TYPE(1008, "Invalid favorite type", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1009, "User not authenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1010, "User not permission", HttpStatus.FORBIDDEN),
    INVALID_PASSWORD(1011, "Password must be between 8 and 20 characters", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(1012, "Invalid request", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1013, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_EXPIRED(1014,"token expired", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;

    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}

