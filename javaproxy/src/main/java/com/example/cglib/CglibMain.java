package com.example.cglib;

import com.example.Xiaoming;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/5/1 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class CglibMain {

    public static void main(String[] params) {
        CglibProxy proxy = new CglibProxy();
        Xiaoming xiaoming = (Xiaoming) proxy.getInstance(new Xiaoming());
        xiaoming.doSomeThing();
    }

}
