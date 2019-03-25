package com.example.muse;

import org.litepal.crud.DataSupport;

public class Photo extends DataSupport{
    private String name;
    private String uri;
    public Photo(String name,String uri){
        this.name=name;
        this.uri=uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
