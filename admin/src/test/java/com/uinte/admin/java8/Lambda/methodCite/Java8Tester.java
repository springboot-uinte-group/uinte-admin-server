package com.uinte.admin.java8.Lambda.methodCite;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: admin
 * @Date: 2019/1/24 0024 16:40
 * @Description:
 */
public class Java8Tester {
    public static void main(String args[]){
        List names = new ArrayList();

        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");
        names.forEach(System.out::println);
        names.forEach(e->System.out.println(e));
    }
}
