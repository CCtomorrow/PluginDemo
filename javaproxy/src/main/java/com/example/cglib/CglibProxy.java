package com.example.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/5/1 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> cglib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，
 * 并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理 <br>
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    /**
     * 创建代理对象
     *
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (target == null) return null;
        Object result;
        System.out.println("事物开始");
        //执行方法
        result = methodProxy.invokeSuper(o, args);
        System.out.println("事物结束");
        return result;
    }

}
