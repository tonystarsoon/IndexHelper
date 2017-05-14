package com.quickindex;

import android.support.annotation.NonNull;

public class Friend implements Comparable<Friend>{
    private String pinYin;
    private String name;

    //使用成员变量生成构造方法：alt+shift+s->o
    public Friend(String name) {
        super();
        this.name = name;
        this.pinYin = PinYinHelper.getPinYin(name);
    }

    public String getPinYin(){
        return pinYin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Friend o) {
        return this.pinYin.compareTo(o.getPinYin());
    }
}
