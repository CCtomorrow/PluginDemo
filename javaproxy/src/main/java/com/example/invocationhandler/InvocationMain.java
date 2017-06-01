package com.example.invocationhandler;

import com.example.IDoThing;
import com.example.Xiaoming;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/5/1 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> 主函数 <br>
 */
public class InvocationMain {

    public static void main(String[] params) {
        // 动态代理，具体的每个真实的类也要先弄好，只是正调用的时候可以根据不同的条件调用不同的方法
        // 或者在调用那个方法的前后做一些事情
        // InvocationHandler只能代理接口
        final Xiaoming real = new Xiaoming();
        // 代理Xiaoming
        IDoThing doThing = (IDoThing) Proxy.newProxyInstance(
                real.getClass().getClassLoader(),
                new Class[]{IDoThing.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object object = method.invoke(real, args);
                        return object;
                    }
                });
        doThing.doSomeThing();

        // 另一写法
//        InvocationProxy proxy = new InvocationProxy();
//        IDoThing iDoThing = (IDoThing) proxy.bind(new Xiaoming());
//        iDoThing.doSomeThing();
    }

    /**
     * 禁止List的add功能
     *
     * @param list
     * @return
     */
    public static List getList(final List list) {
        return (List) Proxy.newProxyInstance(list.getClass().getClassLoader(),
                new Class[]{List.class},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("add".equals(method.getName())) {
                            throw new UnsupportedOperationException();
                        } else {
                            return method.invoke(list, args);
                        }
                    }
                });
    }
}
