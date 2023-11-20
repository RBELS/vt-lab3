package com.adbs.vtlabs.lab3.model.config;

import com.adbs.vtlabs.lab3.config.ErrorMessageConfigurationProperties;
import com.adbs.vtlabs.lab3.exception.ErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorResponse {
    private Integer code;
    private ErrorMessage message;

    @Data
    @Accessors(chain = true)
    public static class ErrorMessage {
        private String langEn;
        private String langRu;
    }

    public ErrorResponse copy() {
        return new ErrorResponse()
                .setCode(code)
                .setMessage(new ErrorMessage()
                        .setLangEn(message.langEn)
                        .setLangRu(message.langRu)
                );
    }

    public static ErrorResponse fromErrorStatus(ErrorCode errorCode) {
        return ErrorMessageConfigurationProperties.errorResponseMap.get(errorCode).copy();
    }
}
