package com.tiger.rpc.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * @ClassName: KyroUtil
 * @Author: Zeng.h
 * @Date: 2023/11/24
 * @Description:
 * @Version: 1.0
 **/
public class KryoUtil {
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        return kryo;
    });

    public static <T> byte[] serialize(T obj) {
        if (obj == null) return new byte[0];
        Kryo kryo = kryoThreadLocal.get();
        Output output = new Output(1024, -1);
        kryo.writeClassAndObject(output, obj);
        output.flush();
        byte[] bytes = output.toBytes();
        output.close();
        return bytes;
    }

    public static <T> T deserialize(byte[] data) {
        if (data == null || data.length == 0) return null;
        Kryo kryo = kryoThreadLocal.get();
        Input input = new Input(data);
        Object object = kryo.readClassAndObject(input);
        input.close();
        return (T) object;
    }
}
