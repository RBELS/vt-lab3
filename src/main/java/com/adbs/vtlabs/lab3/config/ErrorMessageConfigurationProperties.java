package com.adbs.vtlabs.lab3.config;

import com.adbs.vtlabs.lab3.exception.ErrorCode;
import com.adbs.vtlabs.lab3.model.config.ErrorResponse;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "")
@Setter
public class ErrorMessageConfigurationProperties {
    public static Map<ErrorCode, ErrorResponse> errorResponseMap;
    private Map<ErrorCode, ErrorResponse> error;

    @PostConstruct
    void postConstruct() {
        errorResponseMap = error;
    }
}
