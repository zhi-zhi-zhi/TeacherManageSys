package com.cqut.icode.services;

import com.cqut.icode.entities.User;

/**
 * @author 谭强
 * @date 2019/5/16
 */
public interface UserService {

    public boolean login(User user);

    public boolean register(User user);
}
