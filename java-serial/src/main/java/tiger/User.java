package tiger;

import java.io.Serializable;

/**
 * @Author Zenghu
 * @Date 2021/4/14 23:07
 * @Description
 * @Version: 1.0
 **/
public class User implements Serializable {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

