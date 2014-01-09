package com.br.bnsantos.login.example.http.task;

import android.os.AsyncTask;
import com.br.bnsantos.login.example.http.response.BasicResponse;
import org.apache.http.cookie.Cookie;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHttpTask<P, PG> extends AsyncTask<P, PG, BasicResponse> {
    protected Cookie cookie;

    protected abstract String getDebugTag();

    protected abstract String getActionPath();

    protected Class<BasicResponse> getResponseClass(){
        return BasicResponse.class;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}
