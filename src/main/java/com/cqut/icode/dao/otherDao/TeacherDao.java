package com.cqut.icode.dao.otherDao;

import com.cqut.icode.entities.Teacher;

import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/30
 */
public interface TeacherDao {
    /**
     * 从文件或数据库中读取到Teacher信息
     * @return 读取到的Teacher信息组成的List
     */
    List<Teacher> listTeachers();

    /**
     * 将新增的Teacher保存到文件或数据库
     * @param teacher 新增的Teacher信息
     * @return 新增成功为true
     */
    boolean saveTeacher(Teacher teacher);

    /**
     * 将修改的Teacher保存到文件或数据库
     * @param teacher 修改后的Teacher信息
     * @return 修改成功为true
     */
    boolean updateTeacher(Teacher teacher);

    /**
     * 删除Teachers
     * @param ids 要删除的Teachers的id
     * @return 删除成功为true
     */
    boolean removeTeachers(List<Long> ids);
}
