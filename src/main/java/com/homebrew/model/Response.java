package com.homebrew.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Response<T> {
    private T data;
    private String status;
    private long timestamp;
    public Response(ResponseBuilder<T> responseBuilder){
        this.data = responseBuilder.data;
        this.status = responseBuilder.status;
        this.timestamp = responseBuilder.timestamp;
    }
    public static class ResponseBuilder<T>{
        private T data;
        private String status;
        private long timestamp;
        public ResponseBuilder<T> setData(T data){
            this.data = data;
            return this;
        }
        public ResponseBuilder<T> setStatus(String status){
            this.status = status;
            return this;
        }
        public ResponseBuilder<T> setTimestamp(){
            this.timestamp = new Date().getTime();
            return this;
        }
        public Response<T> build(){
            return new Response<>(this);
        }
    }
}
