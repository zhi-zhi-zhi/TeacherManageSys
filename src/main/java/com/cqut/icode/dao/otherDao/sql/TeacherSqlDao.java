package com.cqut.icode.dao.otherDao.sql;

import com.cqut.icode.dao.dbconnection.DBConnection;
import com.cqut.icode.dao.otherDao.TeacherDao;
import com.cqut.icode.entities.Teacher;

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
public class TeacherSqlDao implements TeacherDao {
    private Connection connection = DBConnection.getDBConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    @Override
    public List<Teacher> listTeachers() {
        String sql = "select id, tno, name, gender, age, academy, dept, salary from teacher";
        List<Teacher> result = null;
        Teacher teacher;

        System.out.println(sql);
        try {
            result = new ArrayList<>();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teacher = new Teacher(
                        resultSet.getLong("id"),
                        resultSet.getLong("tno"),
                        resultSet.getString("name"),
                        resultSet.getString("gender"),
                        resultSet.getInt("age"),
                        resultSet.getString("academy"),
                        resultSet.getString("dept"),
                        resultSet.getFloat("salary"));

                result.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        String sql = "insert into teacher(tno, name, gender, age, academy, dept, salary) values ("
                + teacher.getTno() + ", '" + teacher.getName() + "', '" + teacher.getGender() + "', "
                + teacher.getAge() + ", '" + teacher.getAcademy() + "', '" + teacher.getDept() + "', "
                + teacher.getSalary() + ")";

        return executeUpdate(sql);
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        String sql = "update teacher set "
                + "name = '" + teacher.getName()
                + "', gender = '" + teacher.getGender()
                + "', age = " + teacher.getAge()
                + ", academy = '" + teacher.getAcademy()
                + "', dept = '" + teacher.getDept()
                + "', salary = " + teacher.getSalary()
                + " where id = " + teacher.getId();

        return executeUpdate(sql);
    }

    @Override
    public boolean removeTeachers(List<Long> ids) {
        StringBuilder condition = new StringBuilder("(");
        for (Long id : ids) {
            condition.append(id.toString()).append(", ");
        }

        // (1, 2, 3, 4, 5  ----> (1, 2, 3, 4, 5)
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
