package com.homebrew.homebrew.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class S3UploadFailException extends RuntimeException {
    private final String message;
    public S3UploadFailException(String message) {
        this.message = message;
    }
}
