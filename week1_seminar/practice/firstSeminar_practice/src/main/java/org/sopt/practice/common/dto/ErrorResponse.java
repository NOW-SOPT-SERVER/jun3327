package org.sopt.practice.common.dto;

import org.sopt.practice.Exception.ErrorMessage;

public record ErrorResponse(
        int status,
        String message
) {

    public static ErrorResponse of(ErrorMessage errorMessage) {
        return new ErrorResponse(errorMessage.getStatus(), errorMessage.getMessage());
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message);
    }
}
