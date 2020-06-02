package com.tiger.rmi.modle;

import java.io.Serializable;


public class User implements Serializable {
    private static final long serialVersionUID = 7956704725635929146L;
    private String name;
    private char sex;
    private int age;
    private String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", job='" + job + '\'' +
                '}';
    }
}
