package com.example.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/5/1 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能实现JDK的动态代理 <br>
 */
public class InvocationProxy implements InvocationHandler {

    private Object target;

    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target == null) return null;
        Object result;
        System.out.println("事物开始");
        //执行方法
        result = method.invoke(target, args);
        System.out.println("事物结束");
        return result;
    }

}
