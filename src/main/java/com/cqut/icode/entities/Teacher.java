package com.cqut.icode.entities;

import com.cqut.icode.annotation.AutoIncrementId;
import com.cqut.icode.entities.base.BaseEntity;
import com.cqut.icode.annotation.Column;

/**
 * @author 谭强
 * @date 2019/5/11
 */
public class Teacher extends BaseEntity{
    @AutoIncrementId
    @Column(value = "Long")
    private Long id;
    @Column(value = "Long")
    private Long tno;
    @Column(value = "String")
    private String name;
    @Column(value = "String")
    private String gender;
    @Column(value = "Integer")
    private Integer age;
    @Column(value = "String")
    private String academy;
    @Column(value = "String")
    private String dept;
    @Column(value = "Float")
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

    public Teacher(Long id, String name, String gender, Integer age, String academy, String dept, Float salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.academy = academy;
        this.dept = dept;
        this.salary = salary;
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

    @Override
    public String getTableName() {
        return "teacher";
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", academy='" + academy + '\'' +
                ", dept='" + dept + '\'' +
                ", salary=" + salary +
                '}';
    }
}
