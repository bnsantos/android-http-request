package com.br.bnsantos.login.example.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/11/14
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonField implements Parcelable {
    private String name;
    private String value;

    public JsonField(){}

    public JsonField(String name){
        this.name = name;
    }

    public JsonField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private JsonField(Parcel in){
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Creator<JsonField> CREATOR = new Creator<JsonField>() {
        public JsonField createFromParcel(Parcel in) {
            return new JsonField(in);
        }

        public JsonField[] newArray(int size) {
            return new JsonField[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
