package org.sopt.practice.Exception;

import lombok.Getter;

@Getter
public class UserAuthenticationException extends BusinessException {
    public UserAuthenticationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
