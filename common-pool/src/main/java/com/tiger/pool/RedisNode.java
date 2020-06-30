package com.tiger.pool;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: Zeng Hu
 * @Date: 2020/6/28 21:40
 * @Description:
 * @Version: 1.0
 **/
public class RedisNode implements Serializable {
    private static final long serialVersionUID = -304692023834649428L;

    enum State {
        CONNECTED, // 在线
        DISCONNECTED // 掉线
    }

    enum Mode {
        MASTER, // 主节点
        SLAVE  // 从节点
    }

    static class Slot {  // 负责的hash slot
        int start = -1;
        int end = -1;

        public static Slot parse(String slotStr) {
            String[] split = slotStr.split("-");
            Slot slot = new Slot();
            slot.start = Integer.parseInt(split[0]);
            slot.end = Integer.parseInt(split[1]);
            return slot;
        }

        @Override
        public String toString() {
            return start + "-" + end;
        }
    }

    private volatile State state = State.DISCONNECTED;
    private volatile Mode mode = Mode.MASTER;
    private String id;
    private String masterId = "";
    private Set<Slot> slots;
    private String ip;
    private int port;

    private RedisNode() {
        slots = new HashSet<>();

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "RedisNode{" +
                "state=" + state +
                ", mode=" + mode +
                ", id='" + id + '\'' +
                ", masterId='" + masterId + '\'' +
                ", slots=" + slots +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    public boolean containsSlot(int slot) {
        for (Slot slot1 : slots) {
            if (slot <= slot1.end && slot >= slot1.start) {
                return true;
            }
        }
        return false;
    }


    public static RedisNode parser(String node) {
        if (Strings.isNullOrEmpty(node)) {
            throw new IllegalArgumentException("node is empty");
        }
        String[] split = node.split("\\s+");
        try {
            RedisNode redisNode = new RedisNode();
            redisNode.id = split[0];
            redisNode.ip = split[1].substring(0, split[1].indexOf(":"));
            redisNode.port = Integer.parseInt(split[1].substring(split[1].indexOf(":") + 1, split[1].indexOf("@")));
            String[] modes = split[2].split(",");
            for (String modeStr : modes) {
                if ("master".equals(modeStr)) {
                    redisNode.mode = Mode.MASTER;
                    break;
                } else if ("slave".equals(modeStr)) {
                    redisNode.mode = Mode.SLAVE;
                    break;
                }

            }
            if (!"-".equals(split[3])) {
                redisNode.masterId = split[3];
            }
            if ("connected".equals(split[7])) {
                redisNode.state = State.CONNECTED;
            } else {
                redisNode.state = State.DISCONNECTED;
            }
            if (split.length == 8)
                return redisNode;
            for (int i = 8; i < split.length; i++) {
                redisNode.slots.add(Slot.parse(split[i]));
            }
            return redisNode;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        RedisNode redisNode = RedisNode.parser("d20383a5904a63c4eae1b9ea1cbf08d07ba6ceb1 192.168.100.201:8379@18379 master,fail - 1593442910247 1593442910144 1 disconnected 0-5460");
        System.out.println(redisNode.toString());
    }
}
