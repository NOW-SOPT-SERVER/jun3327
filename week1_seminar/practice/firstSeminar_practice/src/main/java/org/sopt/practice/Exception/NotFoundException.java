package org.sopt.practice.exception;

import lombok.Getter;
import org.sopt.practice.common.dto.ErrorMessage;

@Getter
public class NotFoundException extends BusinessException{

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
