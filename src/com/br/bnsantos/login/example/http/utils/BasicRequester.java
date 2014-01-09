package com.br.bnsantos.login.example.http.utils;

import android.util.Log;
import com.br.bnsantos.login.example.http.exception.HttpRequesterException;
import com.br.bnsantos.login.example.http.response.BasicResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicRequester<T extends HttpRequestBase> {
    private static java.lang.String DEBUG_TAG = "[AndroidHttpRequest]" + BasicRequester.class.getName();

    public BasicRequester() {
    }

    public BasicResponse doRequest(T requestBase) throws HttpRequesterException, IOException {
        if (requestBase == null) {
            throw new HttpRequesterException("Invalid request base! Cannot be null!");
        }

        try {
            HttpClient httpClient = new DefaultHttpClient();

            CookieStore cookieStore = new BasicCookieStore();
            HttpContext httpContext = new BasicHttpContext();
            httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

            // Cria base da requisiticao
            Log.d(DEBUG_TAG, requestBase.getURI().getHost());
            Log.d(DEBUG_TAG, requestBase.getURI().getPath());
            ResponseHandler<BasicResponse> responseHandler = new AndroidHttpRequestResponseHandler();
            BasicResponse response = httpClient.execute(requestBase, responseHandler, httpContext);

            //TODO Cookie
            /*
            List<Cookie> cookies = cookieStore.getCookies();
            if (!cookies.isEmpty()) {
                response.setCookie(cookies.get(0));
            }*/

            return response;
        } catch (Exception e) {
            throw new HttpRequesterException("Request failed ", e);
        }
    }
}
