package com.qyai.activityplugin.pm;

import android.content.Context;
import android.content.pm.PackageParser;
import android.os.Build;
import android.util.DisplayMetrics;

import com.qyai.activityplugin.util.ReflectUtil;

import java.io.File;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/12/10 <br>
 * <b>@author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b> PackageParser,注意这个类是hide的，我现在这样可以是因为我使用的是完整android.jar包，在项目根目录下面有 <br>
 */
public class PluginPackageParser {

    private static final int ANDROID_50 = 21;
    private static final int ANDROID_70 = 24;

    /**
     * 解析插件apk
     *
     * @param context context
     * @param apk     插件apk位置，必须是apk文件
     * @param flags   flag
     * @return {@link PackageParser.Package}
     * @throws PackageParser.PackageParserException
     */
    public static final PackageParser.Package parsePackage(final Context context, final File apk, final int flags)
            throws PackageParser.PackageParserException {
        if (Build.VERSION.SDK_INT >= ANDROID_70) {
            return PackageParserV24.parsePackage(context, apk, flags);
        } else if (Build.VERSION.SDK_INT >= ANDROID_50) {
            return PackageParserLollipop.parsePackage(context, apk, flags);
        } else {
            return PackageParserLegacy.parsePackage(context, apk, flags);
        }
    }

    /**
     * 7.0及以后的兼容处理
     */
    private static final class PackageParserV24 {
        static final PackageParser.Package parsePackage(Context context, File apk, int flags)
                throws PackageParser.PackageParserException {
            PackageParser parser = new PackageParser();
            PackageParser.Package pkg = parser.parsePackage(apk, flags);
            ReflectUtil.invokeNoException(PackageParser.class, null, "collectCertificates",
                    new Class[]{PackageParser.Package.class, int.class}, pkg, flags);
            return pkg;
        }
    }

    /**
     * 5.0,5.1,6.0的处理
     */
    private static final class PackageParserLollipop {
        static final PackageParser.Package parsePackage(final Context context, final File apk, final int flags)
                throws PackageParser.PackageParserException {
            PackageParser parser = new PackageParser();
            PackageParser.Package pkg = parser.parsePackage(apk, flags);
            try {
                parser.collectCertificates(pkg, flags);
            } catch (Throwable e) {
                // ignored
            }
            return pkg;
        }
    }

    /**
     * 低版本的处理
     */
    private static final class PackageParserLegacy {
        static final PackageParser.Package parsePackage(Context context, File apk, int flags) {
            String path = apk.getAbsolutePath();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//            Class<?> parserClazz = null;
//            try {
//                parserClazz = Class.forName("android.content.pm.PackageParser");
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
            Object packageParser = null;
            try {
                packageParser = ReflectUtil.invokeConstructor(PackageParser.class, new Class[]{String.class}, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object pkg = ReflectUtil.invokeNoException(PackageParser.class, packageParser,
                    "parsePackage", new Class[]{File.class, String.class, DisplayMetrics.class, int.class},
                    apk, path, metrics, flags);
            // 不用反射实现如下
            // PackageParser parser = new PackageParser(path);
            // PackageParser.Package pkg = parser.parsePackage(apk, path, metrics, flags);
            ReflectUtil.invokeNoException(PackageParser.class, packageParser, "collectCertificates",
                    new Class[]{PackageParser.Package.class, int.class}, pkg, flags);
            return (PackageParser.Package) pkg;
        }
    }

}
