package com.cqut.icode.services.impl;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.dao.EntityDao;
import com.cqut.icode.dao.impl.EntityDaoImpl;
import com.cqut.icode.entities.Teacher;
import com.cqut.icode.entities.User;
import com.cqut.icode.services.TeacherService;

import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/11
 * 对于Service和DAO类，基于SOA的理念，暴露出来的服务一定是接口，内部的实现类用Impl的后缀与接口区别
 */
public class TeacherServiceImpl implements TeacherService {
    @AutoWired
    private static EntityDao<Teacher> dao;

    @Override
    public List<Teacher> listTeachers() {
        // 无任何限制条件

        return dao.listEntities(new Teacher() , Teacher.class);
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        return dao.saveEntity(teacher);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return dao.updateEntity(teacher, Teacher.class);
    }

    @Override
    public boolean removeTeachers(List<Long> ids) {
        return dao.removeEntities(ids, Teacher.class);
    }
}
