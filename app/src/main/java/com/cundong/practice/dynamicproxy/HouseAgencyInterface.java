package com.cundong.practice.dynamicproxy;

/**
 * Created by liucundong on 2016/7/20.
 * <p/>
 * 接口，房屋中介
 */
public interface HouseAgencyInterface {

    /**
     * 买房
     *
     * @param name  购房者姓名
     * @param price 价格
     */
    void buyHouse(String name, float price);
}