package com.example.normal;

import com.example.IDoThing;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/5/1 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class ProxyXiaoming implements IDoThing {

    private IDoThing mSubject;

    public ProxyXiaoming(IDoThing subject) {
        mSubject = subject;
    }

    @Override
    public void doSomeThing() {
        mSubject.doSomeThing();
    }

}
