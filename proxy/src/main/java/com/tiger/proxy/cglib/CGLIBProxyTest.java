package com.tiger.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CGLIBProxyTest {
    public static void main(String[] args) {
        /**
         * 增强类，为需要代理的类的子类，因此父类不能为final,同时方法不能为final
         * 代理类需要实现MethodInterceptor接口
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Caculator.class);  // 设置父类
        enhancer.setCallback(new CaculatorProxy());
        Caculator caculator = (Caculator) enhancer.create();
        double result = caculator.add(1.2, 3.4);
        System.out.println(result);
    }
}
