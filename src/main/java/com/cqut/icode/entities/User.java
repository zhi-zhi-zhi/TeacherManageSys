package com.cqut.icode.entities;

import com.cqut.icode.annotation.Column;
import com.cqut.icode.annotation.AutoIncrementId;
import com.cqut.icode.entities.base.BaseEntity;

/**
 * @author 谭强
 * @date 2019/5/12
 */
public class User extends BaseEntity {
    @AutoIncrementId
    @Column(value = "id")
    private Long id;
    @Column(value = "username")
    private Long username;
    @Column(value = "password")
    private String password;

    public User() {
    }

    public User(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getTableName() {
        return "user";
    }
}
