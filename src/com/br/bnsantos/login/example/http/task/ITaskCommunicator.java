package com.br.bnsantos.login.example.http.task;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ITaskCommunicator<R extends Object> {

    public void onPostExecute(R item);

    public void onCancelled() ;
}

