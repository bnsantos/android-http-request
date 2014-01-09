package com.br.bnsantos.login.example.http.task;

import android.util.Log;
import com.br.bnsantos.login.example.http.response.BasicResponse;
import com.br.bnsantos.login.example.http.rest.HttpMethodType;
import com.br.bnsantos.login.example.http.rest.RestGet;
import com.br.bnsantos.login.example.http.utils.BasicRequester;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpTask<P, PG> extends AbstractHttpTask<P, PG> {
    private final String pathToAction;
    private ITaskCommunicator<BasicResponse> tc = null;
    private HttpMethodType type;

    public HttpTask(ITaskCommunicator<BasicResponse> tc, Cookie cookie, String pathToAction, HttpMethodType type) {
        this.tc = tc;
        this.cookie = cookie;
        this.pathToAction = pathToAction;
        this.type = type;
        Log.d(this.getDebugTag(), "Creating Task");
    }

    @Override
    protected BasicResponse doInBackground(P... params) {
        Log.i(this.getDebugTag(), "Executing Task in background");
        RestGet user;
        try {
            user = new RestGet(this.getActionPath(), cookie);
            BasicRequester<HttpGet> requester = new BasicRequester<HttpGet>();
            return requester.doRequest(user);
        } catch (Exception e) {
            Log.d(this.getDebugTag(), "Failed on request", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(final BasicResponse response) {
        Log.d(this.getDebugTag(), "Task completed [" + ((response != null) ? response.getResponse() : "response null") + "]");
        this.tc.onPostExecute(response);
    }

    @Override
    protected void onCancelled() {
        Log.d(this.getDebugTag(), "Task cancelled");
        this.tc.onCancelled();
    }

    @Override
    protected String getDebugTag() {
        return HttpTask.class.getName();
    }

    @Override
    protected String getActionPath() {
        return this.pathToAction;
    }
}
