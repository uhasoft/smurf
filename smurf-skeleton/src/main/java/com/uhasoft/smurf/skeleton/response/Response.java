package com.uhasoft.smurf.skeleton.response;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class Response<T> {

    private static final int CODE_SUCCESS = 0;
    private static final int CODE_FAILURE = 1;

    private String message;
    private int code;
    private T data;

    public static Response<Void> failure(String message, int code){
        return new Response<>(message, code, null);
    }

    public static Response<Void> failure(String message){
        return new Response<>(message, CODE_FAILURE, null);
    }

    public static <T> Response<T> success(String message, T data){
        return new Response<>(message, CODE_SUCCESS, data);
    }

    public static Response<Void> success(String message){
        return new Response<>(message, CODE_SUCCESS, null);
    }

    public Response(String message, int code, T data){
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
