package com.example.library.base;

import java.util.List;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public class BaseGson<T> {

    /**
     * code : 200
     * status : true
     * data : [{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"},{"id":1,"username":"我的哀愁","password":null,"avatar":"http://bpic.588ku.com/element_origin_min_pic/16/12/03/3a2593984a8813e1942fc72a0a0ecd3c.jpg","sign":"测试签名"}]
     */

    private int code;
    private boolean status;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
