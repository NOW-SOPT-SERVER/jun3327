package org.sopt.practice.common.dto;

import org.sopt.practice.domain.SuccessMessage;

public record SuccessStatusResponse(
        int status,
        String messsage
) {

    public static SuccessStatusResponse of(SuccessMessage successMessage) {
        return new SuccessStatusResponse(successMessage.getStatus(), successMessage.getMessage());
    }
}
