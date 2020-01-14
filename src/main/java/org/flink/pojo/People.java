package org.flink.pojo;

public class People {

    public String name;

    public int age;

    public String phone;

    public People(){}

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "people{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", phone='" + phone + '\'' +
            '}';
    }
}
