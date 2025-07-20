package com.homebrew.exception;

import com.homebrew.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NoSuchPackageFoundException.class})
    public ResponseEntity<ErrorResponse<String>> handleNoSuchPackageFoundException(Exception e) {
        ErrorResponse<String> errorResponse = new ErrorResponse.ErrorResponseBuilder<String>().setMessage(e.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({S3UploadFailException.class})
    public ResponseEntity<ErrorResponse<String>> handleS3UploadFailException(Exception e){
        ErrorResponse<String> errorResponse = new ErrorResponse.ErrorResponseBuilder<String>().setMessage(e.getMessage()).build();
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
