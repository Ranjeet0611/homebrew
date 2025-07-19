package com.homebrew.homebrew.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchPackageFoundException extends RuntimeException {
    private final String message;
    public NoSuchPackageFoundException(String message){
        super(message);
        this.message = message;
    }
}
