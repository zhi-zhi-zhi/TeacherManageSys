package com.cqut.icode.services.impl;

import com.cqut.icode.dao.impl.EntityDaoImpl;
import com.cqut.icode.entities.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 谭强
 * @date 2019/5/11
 * 对于Service和DAO类，基于SOA的理念，暴露出来的服务一定是接口，内部的实现类用Impl的后缀与接口区别
 */
public class TeacherServiceImpl implements com.cqut.icode.services.TeacherService {
    private static EntityDaoImpl<Teacher> dao = new EntityDaoImpl<Teacher>();

    @Override
    public List<Map<String, Object>> listTeachers() {
        // 无任何限制条件
        List<Teacher> teachers = dao.listEntities("" , Teacher.class);

        if (teachers == null) {
            return null;
        }

        List<Map<String, Object>> mapListTeachers = new ArrayList<>();

        Map<String, Object> map = null;
        for (Teacher teacher : teachers) {
            map = new HashMap<>(8);
            map.put("id", teacher.getId());
            map.put("tno", teacher.getTno());
            map.put("name", teacher.getName());
            map.put("gender", teacher.getGender());
            map.put("age", teacher.getAge());
            map.put("academy", teacher.getAcademy());
            map.put("dept", teacher.getDept());
            map.put("salary", teacher.getSalary());
            mapListTeachers.add(map);
        }

        return mapListTeachers;
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
        int result = 0;
        StringBuilder condition = new StringBuilder("(");
        for (Long id : ids) {
            condition.append(id.toString()).append(", ");
        }

        // (1, 2, 3, 4, 5  ----> (1, 2, 3, 4, 5)
        System.out.println(condition.substring(0, condition.length() - 2) + ")");
        return dao.removeEntities(condition.substring(0, condition.length() - 2) + ")", Teacher.class);
    }
}
