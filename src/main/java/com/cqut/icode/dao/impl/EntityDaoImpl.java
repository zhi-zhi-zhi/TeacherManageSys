package com.cqut.icode.dao.impl;

import com.cqut.icode.annotation.AutoIncrementId;
import com.cqut.icode.annotation.Entity;
import com.cqut.icode.dao.EntityDao;
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
public class EntityDaoImpl<T extends BaseEntity> implements EntityDao<T> {
    private Connection connection = DBConnection.getDBConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public T getEntity(T entity) {
        List<T> list = listEntities(entity);
        if (list.size() >= 1) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<T> listEntities(T entity) {
        List<T> result = null;
        String sql;

        try {
            sql = getListEntitiesSql(entity);

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            result = getEntitiesList(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        close();
        return result;
    }

    @Override
    public boolean saveEntity(T entity) {
        String sql = getSaveEntitySql(entity);

        return executeUpdate(sql);
    }


    @Override
    public boolean removeEntities(List<Long> ids, Class<T> tClass) {
        String sql = getRemoveEntitiesSql(ids, tClass);

        return executeUpdate(sql);
    }

    @Override
    public boolean updateEntity(T entity) {
        String sql = getUpdateEntitySql(entity);

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

    private String getListEntitiesSql(T entity) {
        Class<?> tClass = entity.getClass();
        String result = "";
        try {
            StringBuilder fieldsStr, condition;
            // fieldName: 属性名   methodName: 方法名
            String fieldName, methodName;
            // 通过反射获取到的方法 例如： getAge()
            Method method;
            // tClass实体类的所有属性
            Field[] fields = tClass.getDeclaredFields();
            // field的包装类 如：age为Integer
            FieldType annotation;
            // 保存method执行后返回的数据
            Object fieldValue;

            fieldsStr = new StringBuilder();
            condition = new StringBuilder(" where 1 = 1");
            for (Field field : fields) {
                fieldName = field.getName();
                methodName = "get" + fieldName.toUpperCase().charAt(0) + field.getName().substring(1);
                method = tClass.getMethod(methodName);
                fieldValue = method.invoke(entity);

                annotation = field.getAnnotation(FieldType.class);

                fieldsStr.append(fieldName).append(", ");
                if (fieldValue != null) {
                    if ("String".equals(annotation.value())) {
                        condition.append(" and ").append(fieldName).append(" = '").append(fieldValue).append("'");
                    } else {
                        condition.append(" and ").append(fieldName).append(" = ").append(fieldValue);
                    }
                }
            }
            fieldsStr.delete(fieldsStr.length() - 2, fieldsStr.length());

            result = "select " + fieldsStr + " from "
                    + tClass.getAnnotation(Entity.class).value() + condition;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getSaveEntitySql(T entity) {
        String result = "";
        Class<?> tClass;
        Field[] fields;
        StringBuilder fieldsStr, fieldsValueStr;
        try {
            tClass = entity.getClass();
            fields = tClass.getDeclaredFields();
            fieldsStr = new StringBuilder();
            fieldsValueStr = new StringBuilder();

            // example sql: insert into teacher(tno, name) values (?, ?)
            for (Field field : fields) {
                if (!field.isAnnotationPresent(AutoIncrementId.class)) {
                    String fieldName = field.getName();
                    String methodName = "get" + fieldName.toUpperCase().charAt(0)
                            + fieldName.substring(1);
                    Method method = tClass.getMethod(methodName);

                    fieldsStr.append(fieldName).append(", ");
                    fieldsValueStr.append("'").append(method.invoke(entity)).append("', ");
                }
            }
            fieldsStr.delete(fieldsStr.length() - 2, fieldsStr.length());
            fieldsValueStr.delete(fieldsValueStr.length() - 2, fieldsValueStr.length());

            result = "insert ignore into " + tClass.getAnnotation(Entity.class).value()
                    + "(" + fieldsStr + ") values (" + fieldsValueStr + ")";

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getRemoveEntitiesSql(List<Long> ids, Class<T> tClass) {
        StringBuilder condition = new StringBuilder("(");
        for (Long id : ids) {
            condition.append(id.toString()).append(", ");
        }

        // (1, 2, 3, 4, 5  ----> (1, 2, 3, 4, 5)
        System.out.println(condition.delete(condition.length() - 2, condition.length()).append(")"));

        Field[] fields = tClass.getDeclaredFields();
        StringBuilder sql = new StringBuilder("delete from "
                + tClass.getAnnotation(Entity.class).value()
                + " where ");

        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoIncrementId.class)) {
                sql.append(field.getName());
                sql.append(" in ").append(condition).append(" ");
                break;
            }
        }

        return sql.toString();
    }

    private String getUpdateEntitySql(T entity) {
        String result = "";
        Class<?> tClass = entity.getClass();
        Field[] fields = tClass.getDeclaredFields();

        try {
            StringBuilder sql = new StringBuilder();
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

            sql.delete(sql.length() - 2, sql.length());
            sql.append(condition);

            result = "update " + tClass.getAnnotation(Entity.class).value() + " set " + sql.toString();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }




    private List<T> getEntitiesList(T entity) {
        // 从数据库获取到的数据
        List<T> result = null;
        String methodName;
        Field[] fields;
        // field的包装类 如：age为Integer
        FieldType annotation;
        // 保存method执行后返回的数据
        Object fieldValue;
        Class<T> tClass = (Class<T>) entity.getClass();
        T t;

        try {
            fields = tClass.getDeclaredFields();
            result = new ArrayList<>();

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

        } catch (SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }
}
