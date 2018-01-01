### Activity插件化的实现
1. Activity的占坑插件化方式(见 tag activity_plugin_01)
2. 外部apk的解析(见 tag activity_plugin_02)
3. ClassLoader问题(见 tag activity_plugin_02)
4. 资源文件问题(见 tag activity_plugin_02)

注意:在Hook PackageParser的时候，由于我使用的android.jar，位置
/Users/qingyong/Dev/android-sdk-macosx/platforms/android-23/android.jar
我使用的这个jar是用的我放置在项目里面的jar替换的，所以可以看到隐藏api，就没有使用Hook或者VirtualAPK的形式，并且也
不打算换成那种形式，这份代码只是研究插件化使用，不会特别的详细