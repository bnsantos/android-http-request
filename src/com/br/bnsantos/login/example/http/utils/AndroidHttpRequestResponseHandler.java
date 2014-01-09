package com.br.bnsantos.login.example.http.utils;

import com.br.bnsantos.login.example.http.response.BasicResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidHttpRequestResponseHandler implements org.apache.http.client.ResponseHandler<com.br.bnsantos.login.example.http.response.BasicResponse> {
    private static java.lang.String TAG = AndroidHttpRequestResponseHandler.class.getName();

    public AndroidHttpRequestResponseHandler() {
    }

    @Override
    public BasicResponse handleResponse(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        return new BasicResponse(httpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(entity, HTTP.UTF_8));
    }
}
