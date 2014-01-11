package com.br.bnsantos.login.example.http.task;

import android.util.Log;
import com.br.bnsantos.login.example.http.response.BasicResponse;
import com.br.bnsantos.login.example.http.rest.HttpMethodType;
import com.br.bnsantos.login.example.http.rest.RestGet;
import com.br.bnsantos.login.example.http.rest.RestPost;
import com.br.bnsantos.login.example.http.utils.BasicRequester;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
        try {

            switch (this.type){
                case GET:
                    BasicRequester<HttpGet> requesterGet = new BasicRequester<HttpGet>();
                    Log.d(this.getDebugTag(), "Performing RestGet");
                    return requesterGet.doRequest(new RestGet(this.getActionPath(), cookie));
                case POST:
                    BasicRequester<HttpPost> requesterPost = new BasicRequester<HttpPost>();
                    Log.d(this.getDebugTag(), "Performing RestPost");
                    return requesterPost.doRequest(new RestPost(this.getActionPath(), (String)params[0], cookie));
            }

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
