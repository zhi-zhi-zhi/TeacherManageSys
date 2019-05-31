package com.cqut.icode.services.impl;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.dao.EntityDao;
import com.cqut.icode.dao.impl.EntityDaoImpl;
import com.cqut.icode.entities.User;
import com.cqut.icode.services.UserService;

/**
 * @author 谭强
 * @date 2019/5/16
 */
public class UserServiceImpl implements UserService {
    @AutoWired
    private static EntityDao<User> dao;

    @Override
    public boolean login(User user) {
        return dao.getEntity(user) != null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
