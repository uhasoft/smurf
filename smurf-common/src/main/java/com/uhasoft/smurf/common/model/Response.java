package com.uhasoft.smurf.common.model;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class Response<T> {

    public final static int SUCCESS = 0;
    public final static int FAILURE = 1;

    private int status;
    private String message;
    private T data;

    public Response(){}

    public Response(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <D> Response<D> success(D data){
        return new Response<>(SUCCESS, null, data);
    }

    public static <D> Response<D> failure(String message, D data){
        return new Response<>(FAILURE, message, data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
