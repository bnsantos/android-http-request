package com.br.bnsantos.login.example.http.rest;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public enum HttpMethodType {
    GET(0), POST(1), DELETE(2), PUT(3);

    private int type;

    private HttpMethodType(int type){
        this.type=type;
    }

    @Override
    public String toString() {
        switch (type) {
            case 0:
                return "GET";
            case 1:
                return "POST";
            case 2:
                return "DELETE";
            case 3:
                return "PUT";
            default:
                return null;
        }
    }
}
