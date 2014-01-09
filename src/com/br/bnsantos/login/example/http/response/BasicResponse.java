package com.br.bnsantos.login.example.http.response;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicResponse {
    private int status;
    private String response;

    public BasicResponse(int status, String response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
