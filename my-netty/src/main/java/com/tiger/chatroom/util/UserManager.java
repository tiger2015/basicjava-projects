package com.tiger.chatroom.util;

import lombok.extern.slf4j.Slf4j;
import com.tiger.chatroom.entity.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UserManager {

    public static Map<String, User> users = new ConcurrentHashMap<>(22);

    public static void add(User user) {
        users.put(user.getId(), user);
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

    public static List<String> getUserList() {
        if (users.size() == 0) {
            return new ArrayList<>();
        }
        List<String> userList = new ArrayList<>();
        for (String user : users.keySet()) {
            if (users.get(user).isAlive()) {
                userList.add(user);
            }
        }
        Set<String> all = new HashSet<>(users.keySet());
        all.removeAll(userList);
        all.forEach(key->users.remove(key));
        return userList;
    }

}
