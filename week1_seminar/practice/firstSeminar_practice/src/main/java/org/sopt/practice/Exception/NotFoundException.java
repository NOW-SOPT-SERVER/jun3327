package org.sopt.practice.Exception;

import lombok.Getter;

@Getter
public class NotFoundException extends BusinessException{

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
