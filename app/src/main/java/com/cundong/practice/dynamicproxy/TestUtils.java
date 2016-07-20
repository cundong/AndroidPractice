package com.cundong.practice.dynamicproxy;

import java.lang.reflect.Proxy;

/**
 * Created by liucundong on 2016/7/20.
 *
 * TestUtils
 */
public class TestUtils {

    /**
     * 测试动态代理
     *
     * 当你想调用某个接口的实现类（houseAgency）时，不直接调用，而是通过一个HomeLinkInvocationHandler来包装一层，在它里面会通过method.invoke来真实调用
     * houseAgency的接口，同时，还可以在调用前后增加额外代码
     */
    public static void testDynamicProxy() {

        HouseAgencyInterface houseAgency = new HouseAgencyInterfaceImpl();

        HouseAgencyInterface houseAgencyProxy = (HouseAgencyInterface) Proxy.newProxyInstance(houseAgency.getClass().getClassLoader(),
                houseAgency.getClass().getInterfaces(), new HomeLinkInvocationHandler(houseAgency));

        houseAgencyProxy.buyHouse("cundong", 50000);
    }
}