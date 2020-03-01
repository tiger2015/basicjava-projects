package tiger.com.tiger.netty.util;

import tiger.com.tiger.netty.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    private static Map<String, User> users = new ConcurrentHashMap<>(22);

    public static void add(User user) {
        users.putIfAbsent(user.getId(), user);
    }

    public static void remove(String id) {
        User user = users.get(id);
        if (user != null) {
            user.close();
        }
    }

    public static User get(String id) {
        return users.get(id);
    }

    public static List<String> getUserList(){
        if(users.size()==0){
            return new ArrayList<>();
        }
        return new ArrayList<>(users.keySet());
    }

}
