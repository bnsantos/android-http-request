package com.br.bnsantos.login.example.http.exception;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequesterException extends Exception {
    public HttpRequesterException(String message) {
        super(message);
    }

    public HttpRequesterException(String message, Throwable e) {
        super(message, e);
    }
}
