package com.homebrew.cli.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private T data;
    private String status;
    private long timestamp;
}
