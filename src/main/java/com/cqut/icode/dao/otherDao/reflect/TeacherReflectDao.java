package com.cqut.icode.dao.otherDao.reflect;

import com.cqut.icode.dao.dbconnection.DBConnection;
import com.cqut.icode.dao.otherDao.TeacherDao;
import com.cqut.icode.entities.Teacher;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/30
 */
public class TeacherReflectDao implements TeacherDao {
    private Connection connection = DBConnection.getDBConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    @Override
    public List<Teacher> listTeachers() {
        Class<Teacher> teacherClass = Teacher.class;
        Field[] fields = teacherClass.getDeclaredFields();

        StringBuilder fieldsStr = new StringBuilder();

        for (Field field : fields) {
            fieldsStr.append(field.getName()).append(", ");
        }

        fieldsStr.delete(fieldsStr.length() - 2, fieldsStr.length());

        String sql = "select " + fieldsStr.toString() + " from teacher";
        List<Teacher> result = null;
        Teacher teacher;

        System.out.println(sql);
        try {
            result = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teacher = new Teacher();

                for (Field field : fields) {
                    Object object = resultSet.getObject(field.getName());
                    field.setAccessible(true);
                    field.set(teacher, object);
                }

                result.add(teacher);
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        Class<Teacher> teacherClass = Teacher.class;
        Field[] fields = teacherClass.getDeclaredFields();

        StringBuilder fieldsStr = new StringBuilder();
        StringBuilder fieldsValueStr = new StringBuilder();

        for (Field field : fields) {
            if ("id".equals(field.getName())) {
                continue;
            }

            try {
                field.setAccessible(true);
                fieldsStr.append(field.getName()).append(", ");
                fieldsValueStr.append("'").append(field.get(teacher)).append("', ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // tno, name, ..., salary
        fieldsStr.delete(fieldsStr.length() - 2, fieldsStr.length());
        fieldsValueStr.delete(fieldsValueStr.length() - 2, fieldsValueStr.length());

        String sql = "insert into teacher(" + fieldsStr.toString()
                + ") values (" + fieldsValueStr + ")";

        return executeUpdate(sql);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        Class<Teacher> teacherClass = Teacher.class;
        Field[] fields = teacherClass.getDeclaredFields();

        StringBuilder fieldsUpdateStr = new StringBuilder();

        for (Field field : fields) {
            if ("id".equals(field.getName()) || "tno".equals(field.getName())) {
                continue;
            }

            try {
                // append(name = 'tq'),
                field.setAccessible(true);
                fieldsUpdateStr.append(field.getName()).append(" = '")
                        .append(field.get(teacher)).append("', ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        fieldsUpdateStr.delete(fieldsUpdateStr.length() - 2, fieldsUpdateStr.length());

        String sql = "update teacher set " + fieldsUpdateStr + " where id = " + teacher.getId();

        return executeUpdate(sql);
    }

    @Override
    public boolean removeTeachers(List<Long> ids) {
        StringBuilder condition = new StringBuilder("(");
        for (Long id : ids) {
            condition.append(id.toString()).append(", ");
        }

        // (1, 2, 3, 4, 5,  ----> (1, 2, 3, 4, 5)
        System.out.println(condition.delete(condition.length() - 2, condition.length()).append(")"));

        String sql = "delete from teacher where id in " + condition;

        return executeUpdate(sql);
    }

    private boolean executeUpdate(String sql) {
        boolean result = false;
        System.out.println(sql);
        try {
            preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }

    private void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
