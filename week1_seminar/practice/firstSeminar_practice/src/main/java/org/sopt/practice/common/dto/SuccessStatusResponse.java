package org.sopt.practice.common.dto;

import com.sun.net.httpserver.Authenticator;
import org.sopt.practice.domain.SuccessMessage;

public record SuccessStatusResponse<T> (
        int status,
        String messsage,
        T data
) {

    public static SuccessStatusResponse<Void> of(SuccessMessage successMessage) {
        return new SuccessStatusResponse<>(successMessage.getStatus(), successMessage.getMessage(), null);
    }

    public static <T> SuccessStatusResponse<T> of(SuccessMessage successMessage, T data) {
        return new SuccessStatusResponse<>(successMessage.getStatus(), successMessage.getMessage(), data);
    }
}
