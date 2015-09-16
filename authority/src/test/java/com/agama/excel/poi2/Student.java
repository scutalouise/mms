package com.agama.excel.poi2;
import java.util.Date;
/**
 * 需要的实体对象
 * @author XX帅
 * @修改日期 2014-8-19下午2:25:42
 */
public class Student {
    private int id;
    private int age;
    private String name;
    private Date days;
     
    public Student() {
        // TODO Auto-generated constructor stub
    }
    public Student(int id, int age, String name, Date days) {
        super();
        this.id = id;
        this.age = age;
        this.name = name;
        this.days = days;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDays() {
        return days;
    }
    public void setDays(Date days) {
        this.days = days;
    } 
}