package com.br.bnsantos.login.example.http.rest;

import com.google.common.net.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestPost extends HttpPost{
    String item;

    public RestPost(String uri, String item, Cookie cookie) throws UnsupportedEncodingException {
        super(uri);

        this.item = item;
        this.setHeader(com.br.bnsantos.login.example.http.utils.HttpHeaders.APPLICATION_JSON, HttpHeaders.ACCEPT);
        this.setHeader(HttpHeaders.CONTENT_TYPE, com.br.bnsantos.login.example.http.utils.HttpHeaders.APPLICATION_JSON);
        //TODO cookie
        if (cookie != null) {
            this.setHeader(HttpHeaders.COOKIE, cookie.toString());
        }
        StringEntity sEntity = new StringEntity(item);
        this.setEntity(sEntity);
    }
}
