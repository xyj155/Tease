package com.example.library.gson;

import java.util.List;

/**
 * Created by Xuyijie on 2018/10/7.
 */

public class ContactGson {

        /**
         * group : 同学
         * list : [{"id":2,"username":"Privous","password":null,"avatar":"http://img3.imgtn.bdimg.com/it/u=3362427573,2914693748&fm=214&gp=0.jpg","sign":"测试签名"},{"id":7,"username":"冰雨","password":null,"avatar":"http://p.qq181.com/cms/1304/2013040418072939077.jpg","sign":"测试签名"},{"id":8,"username":"我的","password":null,"avatar":"http://p.qq181.com/cms/1304/2013040418073177586.jpg","sign":"测试签名"}]
         */

        private String group;
        private List<ListBean> list;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2
             * username : Privous
             * password : null
             * avatar : http://img3.imgtn.bdimg.com/it/u=3362427573,2914693748&fm=214&gp=0.jpg
             * sign : 测试签名
             */

            private int id;
            private String username;
            private Object password;
            private String avatar;
            private String sign;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }

}
