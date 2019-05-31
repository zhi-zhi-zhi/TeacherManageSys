package com.cqut.icode.services.impl;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.dao.EntityDao;
import com.cqut.icode.dao.otherDao.TeacherDao;
import com.cqut.icode.dao.otherDao.file.TeacherFileDao;
import com.cqut.icode.dao.otherDao.reflect.TeacherReflectDao;
import com.cqut.icode.dao.otherDao.sql.TeacherSqlDao;
import com.cqut.icode.entities.Teacher;
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
    @AutoWired
    private static TeacherDao teacherDao;

    @Override
    public List<Teacher> listTeachers() {
        // 无任何限制条件

        return dao.listEntities(new Teacher());
//        return teacherDao.listTeachers();
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        return dao.saveEntity(teacher);
//        return teacherDao.saveTeacher(teacher);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return dao.updateEntity(teacher);
//        return teacherDao.updateTeacher(teacher);
    }

    @Override
    public boolean removeTeachers(List<Long> ids) {
        return dao.removeEntities(ids, Teacher.class);
//        return teacherDao.removeTeachers(ids);
    }
}
