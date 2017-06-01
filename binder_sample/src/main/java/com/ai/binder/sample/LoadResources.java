package com.ai.binder.sample;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/5/3 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class LoadResources {

    public static class PluginResource {
        public Resources resources;
        public AssetManager assetManager;
        public Resources.Theme theme;
    }

    public static PluginResource getPluginResources(String apkPath, Resources supResource, Resources.Theme supTheme) {
        try {
            PluginResource resource = new PluginResource();
            //创建AssetManager
            AssetManager newAssetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = newAssetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(newAssetManager, apkPath);
            Method ensureStringBlocks = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            ensureStringBlocks.setAccessible(true);
            ensureStringBlocks.invoke(newAssetManager);
            //创建我们自己的Resource
            Resources newResource = new Resources(newAssetManager,
                    supResource.getDisplayMetrics(), supResource.getConfiguration());
            Resources.Theme newTheme = newResource.newTheme();
            newTheme.setTo(supTheme);
            resource.assetManager = newAssetManager;
            resource.resources = newResource;
            resource.theme = newTheme;
            return resource;
        } catch (Exception e) {
        }
        return null;
    }

}
