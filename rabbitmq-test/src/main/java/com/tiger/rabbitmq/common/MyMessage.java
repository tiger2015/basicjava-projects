package com.tiger.rabbitmq.common;

import java.io.*;

/**
 * @ClassName MyMessage
 * @Description TODO
 * @Author zeng.h
 * @Date 2020/6/2 14:54
 * @Version 1.0
 **/
public class MyMessage implements Serializable {
    private static final long serialVersionUID = 8289615125161784945L;

    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


    public static byte[] convertToByte(Object object) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static MyMessage convertToObject(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            Object object = ois.readObject();
            return (MyMessage) object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
