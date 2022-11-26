package tiger;

import java.io.*;

public class SerializationTest {

    public static void main(String[] args) {
        User user = new User();
        user.setName("test");
        user.setAge(10);
        User.MAX_AGE = 120;
        byte[] buffer = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(user);
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            User copy = (User) ois.readObject();
            System.out.println(copy.MAX_AGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
