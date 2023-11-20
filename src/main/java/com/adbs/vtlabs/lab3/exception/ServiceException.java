package com.adbs.vtlabs.lab3.exception;

import com.adbs.vtlabs.lab3.config.ErrorMessageConfigurationProperties;
import com.adbs.vtlabs.lab3.model.config.ErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Slf4j
public class ServiceException extends RuntimeException {
    private ErrorResponse errorResponse;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.name());
        errorResponse = ErrorMessageConfigurationProperties.errorResponseMap.get(errorCode).copy();
    }

    @Override
    public String getMessage() {
        return errorResponse.toString();
    }
}
