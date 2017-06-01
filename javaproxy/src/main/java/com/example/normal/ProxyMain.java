package com.example.normal;

import com.example.Xiaoming;

public class ProxyMain {

    public static void main(String[] params) {
        Xiaoming real = new Xiaoming();
        // 这个代理了xiaoming，如果想要再代理别人，需要重新创建ProxyXiaohuang等等
        ProxyXiaoming proxy = new ProxyXiaoming(real);
        // 要访问Xiaoming的doSomeThing()通过ProxyXiaoming去访问
        proxy.doSomeThing();
    }

}
