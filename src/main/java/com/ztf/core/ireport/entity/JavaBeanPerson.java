package com.ztf.core.ireport.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/11.
 */
public class JavaBeanPerson {
    private String name;    // 姓名
    private String sex;     // 性别
    private String age;        // 年龄
    private String hometown;// 籍贯
    private String phone;   // 电话号码

    public JavaBeanPerson() {}


    public JavaBeanPerson(String name, String sex, String age, String hometown, String phone) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.hometown = hometown;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static List<JavaBeanPerson> getList() {
        List<JavaBeanPerson> list = new ArrayList<JavaBeanPerson>();
        list.add(new JavaBeanPerson("a姓名", "female", "10", "Hubei", "10086"));
       list.add(new JavaBeanPerson("Lily李","female", "22", "Hubei", "10086"));
        list.add(new JavaBeanPerson("Macro往", "male", "33", "Beijing", "13800"));
        //        list.add(new JavaBeanPerson("Andy", "male", "44", "HongKong", "13812345678"));
//        list.add(new JavaBeanPerson("Linder", "female", "28", "Guangxi", "18677778888"));
//        list.add(new JavaBeanPerson("Jessie", "female", "26", "Gansu", "18219177720"));
        return list;
    }
}