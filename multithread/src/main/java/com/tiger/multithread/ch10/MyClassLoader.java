package com.tiger.multithread.ch10;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// 自定义类加载器
public class MyClassLoader extends ClassLoader {
    private final Path classDir; // class文件路径

    public MyClassLoader(Path classDir) {
        super();
        this.classDir = classDir;
    }

    public MyClassLoader(Path classDir, ClassLoader parent) {
        super(parent);
        this.classDir = classDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = readClass(name);
        if (bytes == null || bytes.length <= 0) {
            throw new ClassNotFoundException(name + " could not found");
        }
       // System.out.println("load class:" + name);
        // 通过字节构造class对象
        return this.defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 从class文件中读取数据
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private byte[] readClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        Path path = classDir.resolve(Paths.get(classPath + ".class"));
        // System.out.println(path.toAbsolutePath().toString());
        if (!path.toFile().exists()) {
            throw new ClassNotFoundException(name + " could not found");
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Files.copy(path, baos);
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            throw new ClassNotFoundException(name + " could not found");
        } catch (IOException e) {
            throw new ClassNotFoundException(name + " could not found");
        }
    }
}
