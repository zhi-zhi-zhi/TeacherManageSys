package com.cqut.icode.entities;

import com.cqut.icode.annotation.GeneratedValue;
import com.cqut.icode.annotation.Entity;
import com.cqut.icode.entities.base.BaseEntity;

/**
 * @author 谭强
 * @date 2019/5/11
 */
@Entity(name = "teacher")
public class Teacher extends BaseEntity implements Cloneable{
    @GeneratedValue
    private Long id;
    private Long tno;
    private String name;
    private String gender;
    private Integer age;
    private String academy;
    private String dept;
    private Float salary;

    public Teacher() {
    }

    public Teacher(String name, String gender, Integer age, String academy, String dept, Float salary) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.academy = academy;
        this.dept = dept;
        this.salary = salary;
    }

    public Teacher(Long id, Long tno, String name, String gender, Integer age, String academy, String dept, Float salary) {
        this.id = id;
        this.tno = tno;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.academy = academy;
        this.dept = dept;
        this.salary = salary;
    }

    @Override
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", tno=" + tno +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", academy='" + academy + '\'' +
                ", dept='" + dept + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTno() {
        return tno;
    }

    public void setTno(Long tno) {
        this.tno = tno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
}
