package com.example.annotation.util.scheduler;

import java.util.concurrent.Callable;

public class CallableExtension implements Callable<Boolean> {

    private String name;

    public String getName(){
        return name;
    }

    public CallableExtension(String name){
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        return null;
    }
}