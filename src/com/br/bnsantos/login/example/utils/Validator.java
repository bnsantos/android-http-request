package com.br.bnsantos.login.example.utils;

import com.google.common.net.HostSpecifier;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/8/14
 * Time: 11:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Validator {
    public static boolean validateServerAddress(String server){
        return HostSpecifier.isValid(server);
        //return false;
    }
}
