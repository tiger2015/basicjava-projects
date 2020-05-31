package com.tiger.rmi.serviceimpl;

import com.tiger.rmi.modle.User;
import com.tiger.rmi.service.UserService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    public UserServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public User get(String name) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.connect();
            Map<String, String> map = jedis.hgetAll(String.format("user:%s:info:hash", name));
            User user = new User();
            user.setName(name);
            user.setAge(Integer.parseInt(map.get("age")));
            user.setJob(map.get("job"));
            user.setSex(map.get("sex").charAt(0));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.close(jedis);
        }
        return null;
    }

    @Override
    public void save(User user) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.connect();
            Map<String, String> map = new HashMap<>();
            map.put("name", user.getName());
            map.put("age", String.valueOf(user.getAge()));
            map.put("sex", String.valueOf(user.getSex()));
            map.put("job", user.getJob());
            jedis.hmset(String.format("user:%s:info:hash", user.getName()), map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.close(jedis);
        }

    }
}
