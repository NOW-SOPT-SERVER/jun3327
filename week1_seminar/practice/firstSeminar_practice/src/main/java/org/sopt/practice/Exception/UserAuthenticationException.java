package org.sopt.practice.exception;

import lombok.Getter;
import org.sopt.practice.common.dto.ErrorMessage;

@Getter
public class UserAuthenticationException extends BusinessException {
    public UserAuthenticationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
