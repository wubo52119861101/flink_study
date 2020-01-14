package org.flink.pojo;

public class Student {

    public Integer id;

    public String name;

    public String password;

    public Integer age;

    public Student(){}

    public Student(Integer id,String name, String password,Integer age){

        this.id=id;
        this.name=name;
        this.password=password;
        this.age=age;
    }

    @Override
    public String toString() {
        return "id "+id+" name"+name+" password"+password+" age "+age;
    }
}
