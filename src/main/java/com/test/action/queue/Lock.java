package com.test.action.queue;

/**
 * Created by pangming on 2017/5/8.
 */
public class Lock {

    private String name;
    private int type;

    public Lock(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
