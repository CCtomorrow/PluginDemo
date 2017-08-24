package com.yong.reflection.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

/**
 * <b>Project:</b> PluginDemo <br>
 * <b>Create Date:</b> 2017/8/24 <br>
 * <b>Author:</b> qy <br>
 * <b>Address:</b> qingyongai@gmail.com <br>
 * <b>Description:</b>  <br>
 */
public class Asmain {

    public static void main(String[] args) {

        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
                //打印出父类name和本类name
                System.out.println(superName + " " + name);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                //打印出方法名和类型签名
                System.out.println(name + " " + desc);
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        //读取静态内部类
        ClassReader cr = null;
        try {
            cr = new ClassReader("com.yong.reflection.asm.Asmain$Sam");
            cr.accept(visitor, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ClassReader classReader = new ClassReader(Sam.class.getName());
            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
            classReader.accept(classWriter, Opcodes.ASM5);
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "addedMethod", "(Ljava/lang/String;)V", null, null);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitEnd();
            // 获取生成的class文件对应的二进制流
            byte[] code = classWriter.toByteArray();
            //将二进制流写到out/下
            FileOutputStream fos = new FileOutputStream("./javareflection/Sm.class");
            fos.write(code);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class Sam {

        private String name;

        public Sam(String name) {
            this.name = name;
        }

        private long getAge() {
            return 25;
        }

        private void Say() {
            System.out.println("你是不是傻...");
        }

    }

}
