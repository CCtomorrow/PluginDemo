package com.yong.plugin;

import android.content.Context;
import android.widget.Toast;

/**
 * <b>Project:</b> com.yong.plugin <br>
 * <b>Create Date:</b> 2016/9/4 <br>
 * <b>Author:</b> qingyong <br>
 * <b>Description:</b>  <br>
 */
public class ShowToastImpl implements IShowToast {
    @Override
    public int showToast(Context context) {
        Toast.makeText(context, "我来自另一个dex文件", Toast.LENGTH_LONG).show();
        return 100;
    }
}
