package com.cqut.icode.services;

import com.cqut.icode.entities.Teacher;

import java.util.List;
import java.util.Map;

/**
 * @author 谭强
 * @date 2019/5/16
 */
public interface TeacherService {

    /**
     * 获取所有教师对象
     * @return 所有教师对象键值对的集合
     **/
    public List<Map<String, Object>> listTeachers();

    /**
     * 向数据库中插入一条教师数据
     * @param teacher 准备插入的教师数据
     * @return 插入成功为true
     **/
    public boolean saveTeacher(Teacher teacher);

    /**
     * 删除多条教师数据
     * @param ids 准备删除的教师数据主键集合
     * @return 删除成功为true
     **/
    public boolean removeTeachers(List<Long> ids);

    /**
     * 更新一条教师数据
     * @param teacher 准备修改的教师修改后的信息
     * @return 修改成功为true
     **/
    public boolean updateTeacher(Teacher teacher);

}
