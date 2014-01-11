package com.br.bnsantos.login.example.utils;

import com.br.bnsantos.login.example.entities.JsonField;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/11/14
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {

    public static String formatJsonRequest(ArrayList<JsonField> fields){
        if(fields!=null && fields.size()>0){
            StringBuffer json = new StringBuffer("{");
            for(JsonField field : fields){
                if(field.getValue()!=null)
                    json.append("\"" + field.getName() + "\":\"" + field.getValue() + "\",");
            }
            json.delete(json.lastIndexOf(","), json.length());
            json.append("}");
            return json.toString();
        }
        return "";
    }
}
