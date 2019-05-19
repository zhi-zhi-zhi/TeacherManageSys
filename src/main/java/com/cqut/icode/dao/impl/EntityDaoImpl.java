package com.cqut.icode.dao.impl;

import com.cqut.icode.annotation.AutoIncrementId;
import com.cqut.icode.dao.dbconnection.DBConnection;
import com.cqut.icode.annotation.Column;
import com.cqut.icode.entities.base.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/11
 */
public class EntityDaoImpl<T extends BaseEntity> implements com.cqut.icode.dao.EntityDao<T> {
    private Connection connection = DBConnection.getDBConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public T getEntity(String condition, Class<T> tClass) {
        List<T> list = listEntities(condition, tClass);
        if (list.size() >= 1) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<T> listEntities(String condition, Class<T> tClass) {
//        Class<T> tClass =
        System.out.println("list entities");

        List<T> result = new ArrayList<>();
        T entity = null;

        Field[] fields = tClass.getDeclaredFields();
        Column annotation = null;

        StringBuilder sql;
        try {
            // select * from (entity.getTableName()) where 1 = 1 and id = x and xx = xx
            sql = new StringBuilder("select * from "
                    + tClass.getMethod("getTableName").invoke(tClass.newInstance())
                    + condition);
            preparedStatement = connection.prepareStatement(sql.toString());
            // 获取结果集
            resultSet = preparedStatement.executeQuery();
            // 结果集的元祖注入到entity对象
            while (resultSet.next()) {
                entity = tClass.newInstance();

                for (int i = 0; i < fields.length; i++) {
                    annotation = fields[i].getAnnotation(Column.class);

                    String methodName = "set" + fields[i].getName().toUpperCase().charAt(0)
                            + fields[i].getName().substring(1);
                    switch (annotation.value()) {
                        case "String": {
                            tClass.getMethod(methodName, String.class)
                                    .invoke(entity, resultSet.getString(i + 1));
                            break;
                        }
                        case "Integer": {
                            tClass.getMethod(methodName, Integer.class)
                                    .invoke(entity, resultSet.getInt(i + 1));
                            break;
                        }
                        case "Float": {
                            tClass.getMethod(methodName, Float.class)
                                    .invoke(entity, resultSet.getFloat(i + 1));
                            break;
                        }
                        case "Long": {
                            tClass .getMethod(methodName, Long.class)
                                    .invoke(entity, resultSet.getLong(i + 1));
                            break;
                        }
                        default:
                    }

                }

                result.add(entity);
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean saveEntity(T entity) {
        boolean result = false;
        Class<?> tClass = entity.getClass();

        Field[] fields = tClass.getDeclaredFields();

        try {
            // example sql: insert into teacher
            StringBuilder sql = new StringBuilder("insert into " +
                    tClass.getMethod("getTableName").invoke(entity) + "(");

            // example sql: insert into teacher(tno, name) values (?, ?)
            for (int i = 1; i < fields.length; i++) {
                sql.append(fields[i].getName()).append(", ");
            }
            // insert into teacher(tno, name,
            // ---->
            // insert into teacher(tno ,name) values(?
            sql.delete(sql.length() - 2, sql.length()).append(") values(?");

            for (int i = 2; i < fields.length; i++) {
                sql.append(", ?");
            }
            // insert into teacher(tno, name) values(?, ?)
            sql.append(")");

            System.out.println("sql: " + sql);

            preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 1; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                // example methodName: getName
                String methodName = "get" + fieldName.toUpperCase().charAt(0)
                        + fieldName.substring(1);

                Method method = tClass.getMethod(methodName);
                Object fieldValue = method.invoke(entity);

                if (fieldValue != null) {
                    System.out.println(fieldName + ": " + fieldValue.toString());
                    // 将占位符替换为真正的数据
                    preparedStatement.setString(i + 1, fieldValue.toString());
                }
            }

            // 插入成功返回 (1 > 0)
            result = preparedStatement.executeUpdate() > 0;

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }


    @Override
    public boolean removeEntities(String condition, Class<T> tClass) {
        boolean result = false;
        Field[] fields = tClass.getDeclaredFields();
        try {
            StringBuilder sql = new StringBuilder("delete from "
                    + tClass.getMethod("getTableName").invoke(tClass.newInstance())
                    + " where ");

            for (Field field : fields) {
                // 没搞懂什么意思
                // Reports any attempts to reflectively check for the presence of an annotation
                // which is not defined as being retained at runtime.
                // Using Class.isAnnotationPresent() to test for an annotation
                // which has source retention or class-file retention (the default)
                // will always result in a negative result, but is easy to do inadvertently.
                System.out.println("field name: " + field.getName());
                System.out.println("field have Id.class annotation: " + field.isAnnotationPresent(AutoIncrementId.class));

                if (field.isAnnotationPresent(AutoIncrementId.class)) {
                    sql.append(field.getName());
                    sql.append(" in ").append(condition).append(" ");
                    break;
                }
            }


            System.out.println("sql: " + sql);
            preparedStatement = connection.prepareStatement(sql.toString());

            result = preparedStatement.executeUpdate() > 0;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }


        close();
        return result;
    }

    @Override
    public boolean updateEntity(T entity, Class<T> tClass) {
        boolean result = false;
        Field[] fields = tClass.getDeclaredFields();

        try {
            StringBuilder sql = new StringBuilder("update " + entity.getTableName() + " set ");
            StringBuilder condition = new StringBuilder(" where ");
            for (Field field : fields) {
                String fieldName = field.getName();
                String methodName = "get" + fieldName.toUpperCase().charAt(0)
                        + fieldName.substring(1);

                Method method = tClass.getMethod(methodName);
                if (field.isAnnotationPresent(AutoIncrementId.class)) {
                    // example: id = 3;
                    condition.append(field.getName()).append(" = ").append(method.invoke(entity));
                } else {
                    // example: name = xx, age = 20,
                    sql.append(fieldName).append(" = '").append(method.invoke(entity)).append("', ");
                }
            }
            // name = xx, age = 20,
            // ----->
            // name = xx, age = 20
            sql.delete(sql.length() - 2, sql.length());
            sql.append(condition);
            System.out.println("sql: " + sql);
            preparedStatement = connection.prepareStatement(sql.toString());
            result = preparedStatement.executeUpdate() > 0;
        } catch (NoSuchMethodException | SQLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }


        return result;
    }

    private void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
