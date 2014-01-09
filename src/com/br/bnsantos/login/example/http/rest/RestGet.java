package com.br.bnsantos.login.example.http.rest;

import com.google.common.net.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestGet extends HttpGet{
    public RestGet(String uri, Cookie cookie) throws UnsupportedEncodingException {
        super(uri);

        this.setHeader(HttpHeaders.ACCEPT, "application/json");
        if (cookie != null) {
            this.setHeader(HttpHeaders.COOKIE, cookie.toString());
        }
    }
}
