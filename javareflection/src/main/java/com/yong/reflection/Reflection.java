package com.yong.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    private String mm;

    private List<String> mList;

    public Reflection(String v) {
        mm = v;
    }

    public static void main(String[] ps) {
        try {
            Class.forName("");
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        Class<?> c = sb.getClass();
        System.out.println(c.isInstance(sb));
        System.out.println(sb instanceof StringBuilder);


        getClazz();

    }

    private void in() {
        System.out.println(mm);
    }

    private static void getClazz() {
        Class<Reflection> c = Reflection.class;
//        try {
//            Reflection r = c.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        c.isInterface();

        try {
            Constructor constructor = c.getConstructor(String.class);
            try {
                Object o = constructor.newInstance("378");
                Method method = c.getDeclaredMethod("in", (Class<?>[]) null);
                method.setAccessible(true);
                method.invoke(o, (Object[]) null);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Class<String> s = String.class;
        try {
            Constructor constructor = s.getConstructor(String.class);
            try {
                Object o = constructor.newInstance("378");
                System.out.println(o);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        c.getDeclaredMethods();
        c.getMethods();

        c.getDeclaredFields();
        c.getFields();

        Class<Integer> i = int.class;

        ArrayList list = new ArrayList();
        Class<?> l = list.getClass();

        try {
            createArray();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Field field = Reflection.class.getDeclaredField("mList");
            // 当类型可能有类型参数时用此方法,如List<T>
            Type type = field.getGenericType();

            Class<?> aClass = field.getClass();
            // 如果没有类型参数，可强制转型为Class<?>，和上面的一样的意思
            Class<?> bClass = (Class<?>) field.getGenericType();

            //如果类属性的类型带有类型参数，如List<T>
            //那么想获取类型T时用field.getGenericType();方法,然后转型为参数化类型[ParameterizedType]
            if (type instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) type;
                Type[] actualTypes = paramType.getActualTypeArguments();
                for (Type aType : actualTypes) {
                    if (aType instanceof Class) {
                        Class clz = (Class) aType;
                        System.out.println(clz.getName()); //输出java.lang.String
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    public static void createArray() throws ClassNotFoundException {
        Class<?> cls = Class.forName("java.lang.String");
        Object array = Array.newInstance(cls, 3);
        //往数组里添加内容
        Array.set(array, 0, "OK");
        Array.set(array, 1, "HOW ARE YOU");
        Array.set(array, 2, "Fine");
        //获取某一项的内容
        System.out.println(Array.get(array, 2));
    }

}
