package com.tiger.multithread.ch10;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author Zenghu
 * @Date 2022年04月23日 10:48
 * @Description
 * @Version: 1.0
 **/
public class CustomStringLoader extends ClassLoader {

    private String path;

    public CustomStringLoader(String path) {
        super(null);
        this.path = path;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            if (name.startsWith("java.")) {  // 防止找不到java.lang.Object
                System.out.println("load:" + name);
               return super.loadClass(name, resolve);
            }
            Class<?> loadedClass = findLoadedClass(name);
            if (loadedClass == null) {
                loadedClass = findClass(name);
            }
            if (resolve) {
                resolveClass(loadedClass);
            }
            return loadedClass;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classFile = getClassFile(name);
        byte[] classStream = getClassStream(classFile);
        if (classStream.length == 0) {
            throw new ClassNotFoundException(name);
        }
        // predefineClass会检查类的全限定名java.
        return defineClass(name, classStream, 0, classStream.length);
    }


    private String getClassFile(String name) {
        return path + "\\" + name.replace('.', '\\') + ".class";
    }


    private byte[] getClassStream(String filePath) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);) {
            byte[] buffer = new byte[2048];
            int len;
            while ((len = bufferedInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
