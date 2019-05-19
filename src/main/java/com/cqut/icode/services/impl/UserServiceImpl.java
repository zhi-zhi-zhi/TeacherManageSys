package com.cqut.icode.services.impl;

import com.cqut.icode.dao.impl.EntityDaoImpl;
import com.cqut.icode.entities.User;
import com.cqut.icode.services.UserService;

/**
 * @author 谭强
 * @date 2019/5/16
 */
public class UserServiceImpl implements UserService {
    private static EntityDaoImpl<User> dao = new EntityDaoImpl();

    @Override
    public boolean login(User user) {
        String condition = " where username = '" + user.getId()
                + "' and password = '" + user.getPassword() + "'";
        return dao.getEntity(condition, User.class) != null;
    }

    @Override
    public boolean register(User user) {
        return false;
    }
}
