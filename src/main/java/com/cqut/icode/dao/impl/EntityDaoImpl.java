package com.cqut.icode.dao.impl;

import com.cqut.icode.annotation.AutoIncrementId;
import com.cqut.icode.annotation.Table;
import com.cqut.icode.dao.dbconnection.DBConnection;
import com.cqut.icode.annotation.FieldType;
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

    @Override
    public T getEntity(T entity, Class<T> tClass) {
        List<T> list = listEntities(entity, tClass);
        if (list.size() >= 1) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<T> listEntities(T entity, Class<T> tClass) {
        // 从数据库获取到的数据
        List<T> result = null;
        /*
        sql: 拼接好后准备执行的sql语句 例如：select name, age from teacher where 1 = 1 and age = 28
        needColumns: 需要获取哪些列名 例如：(name, age)
        columnCondition: 查询的限制条件 例如：age = 28
         */
        StringBuilder sql, needColumns, columnCondition;
        // fieldName: 属性名   methodName: 方法名
        String fieldName, methodName;
        // 通过反射获取到的方法 例如： getAge()
        Method method;
        // tClass实体类的所有属性
        Field[] fields;
        // field的包装类 如：age为Integer
        FieldType annotation;
        // 保存method执行后返回的数据
        Object fieldValue;
        T t;

        try {
            result = new ArrayList<>();
            needColumns = new StringBuilder();
            columnCondition = new StringBuilder(" where 1 = 1");
            fields= tClass.getDeclaredFields();

            for (Field field : fields) {
                fieldName = field.getName();
                needColumns.append(fieldName).append(", ");
                methodName = "get" + fieldName.toUpperCase().charAt(0) + field.getName().substring(1);
                method = tClass.getMethod(methodName);
                annotation = field.getAnnotation(FieldType.class);
                fieldValue = method.invoke(entity);

                if (fieldValue != null) {
                    if ("String".equals(annotation.value())) {
                        columnCondition.append(" and ").append(fieldName).append(" = '").append(fieldValue).append("'");
                    } else {
                        columnCondition.append(" and ").append(fieldName).append(" = ").append(fieldValue);
                    }
                }
            }
            needColumns.delete(needColumns.length() - 2, needColumns.length());

            // select * from teacher where 1 = 1 and name = x and xx = xx
            sql = new StringBuilder("select " + needColumns +" from "
                    + tClass.getAnnotation(Table.class).value() + columnCondition);
            System.out.println("sql: " + sql);

            preparedStatement = connection.prepareStatement(sql.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = tClass.newInstance();

                for (Field field : fields) {
                    annotation = field.getAnnotation(FieldType.class);
                    methodName = "set" + field.getName().toUpperCase().charAt(0) + field.getName().substring(1);

                    fieldValue = resultSet.getObject(field.getName());
                    switch (annotation.value()) {
                        case "String": {
                            tClass.getMethod(methodName, String.class).invoke(t, (String) fieldValue);
                            break;
                        }
                        case "Integer": {
                            tClass.getMethod(methodName, Integer.class).invoke(t, (Integer) fieldValue);
                            break;
                        }
                        case "Float": {
                            tClass.getMethod(methodName, Float.class).invoke(t, (Float) fieldValue);
                            break;
                        }
                        case "Long": {
                            tClass.getMethod(methodName, Long.class).invoke(t, (Long) fieldValue);
                            break;
                        }
                        default:
                    }
                }

                result.add(t);
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException
                | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }

    @Override
    public boolean saveEntity(T entity) {
        boolean result = false;
        Class<?> tClass = entity.getClass();

        Field[] fields = tClass.getDeclaredFields();

        try {
            // example sql: insert ignore into teacher

            StringBuilder sql = new StringBuilder("insert ignore into " +
                    tClass.getAnnotation(Table.class).value() + "(");

            StringBuilder columnValue = new StringBuilder("(");

            // example sql: insert into teacher(tno, name) values (?, ?)
            for (Field field : fields) {
                if (!field.isAnnotationPresent(AutoIncrementId.class)) {
                    String fieldName = field.getName();
                    String methodName = "get" + fieldName.toUpperCase().charAt(0)
                            + fieldName.substring(1);
                    Method method = tClass.getMethod(methodName);
                    FieldType annotation = field.getAnnotation(FieldType.class);

                    sql.append(fieldName).append(", ");

                    if ("String".equals(annotation.value())) {
                        columnValue.append("'").append(method.invoke(entity)).append("', ");
                    } else {
                        columnValue.append(method.invoke(entity)).append(", ");
                    }
                }
            }

            sql.delete(sql.length() - 2, sql.length()).append(") values ");
            columnValue.delete(columnValue.length() - 2, columnValue.length()).append(")");

            sql.append(columnValue.toString());

            System.out.println("sql: " + sql);

            // 插入成功返回 true(1 > 0)，若主键重复则false(0 > 0)
            preparedStatement = connection.prepareStatement(sql.toString());
            result = preparedStatement.executeUpdate() > 0;

        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | SQLException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }


    @Override
    public boolean removeEntities(List<Long> ids, Class<T> tClass) {
        StringBuilder condition = new StringBuilder("(");
        for (Long id : ids) {
            condition.append(id.toString()).append(", ");
        }

        // (1, 2, 3, 4, 5  ----> (1, 2, 3, 4, 5)
        System.out.println(condition.delete(condition.length() - 2, condition.length()).append(")"));

        boolean result = false;
        Field[] fields = tClass.getDeclaredFields();
        try {
            StringBuilder sql = new StringBuilder("delete from "
                    + tClass.getAnnotation(Table.class).value()
                    + " where ");

            for (Field field : fields) {
                System.out.println("field name: " + field.getName());

                if (field.isAnnotationPresent(AutoIncrementId.class)) {
                    sql.append(field.getName());
                    sql.append(" in ").append(condition).append(" ");
                    break;
                }
            }

            System.out.println("sql: " + sql);
            preparedStatement = connection.prepareStatement(sql.toString());

            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
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
            StringBuilder sql = new StringBuilder("update "
                    + tClass.getAnnotation(Table.class).value() + " set ");
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

        close();
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
