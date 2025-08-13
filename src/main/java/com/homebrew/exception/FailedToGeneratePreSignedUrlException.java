package com.homebrew.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FailedToGeneratePreSignedUrlException extends RuntimeException {
    String message;
    public FailedToGeneratePreSignedUrlException(String message) {
        super(message);
        this.message = message;
    }
}
