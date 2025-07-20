package com.homebrew.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Response<T> {
    private T data;
    private String status;
    private long timeStamp;
    public Response(ResponseBuilder<T> responseBuilder){
        this.data = responseBuilder.data;
        this.status = responseBuilder.status;
        this.timeStamp = responseBuilder.timeStamp;
    }
    public static class ResponseBuilder<T>{
        private T data;
        private String status;
        private long timeStamp;
        public ResponseBuilder<T> setData(T data){
            this.data = data;
            return this;
        }
        public ResponseBuilder<T> setStatus(String status){
            this.status = status;
            return this;
        }
        public ResponseBuilder<T> setTimeStamp(){
            this.timeStamp = new Date().getTime();
            return this;
        }
        public Response<T> build(){
            return new Response<>(this);
        }
    }
}
