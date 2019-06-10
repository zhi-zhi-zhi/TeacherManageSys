package com.cqut.icode.entities;

import com.cqut.icode.annotation.Entity;
import com.cqut.icode.annotation.GeneratedValue;
import com.cqut.icode.entities.base.BaseEntity;

/**
 * @author 谭强
 * @date 2019/6/9
 */
@Entity(name = "girl")
public class Girl extends BaseEntity {
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;

    public Girl() {
    }

    public Girl(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
