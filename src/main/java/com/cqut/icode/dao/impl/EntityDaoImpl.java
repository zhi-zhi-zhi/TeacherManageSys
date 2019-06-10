package com.cqut.icode.dao.impl;

import com.cqut.icode.annotation.GeneratedValue;
import com.cqut.icode.annotation.Entity;
import com.cqut.icode.dao.EntityDao;
import com.cqut.icode.dao.dbconnection.DBConnection;
import com.cqut.icode.entities.Page;
import com.cqut.icode.entities.base.BaseEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 谭强
 * @date 2019/5/11
 */
public class EntityDaoImpl<T extends BaseEntity> implements EntityDao<T> {
    private Connection connection = DBConnection.getDBConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public T getEntityById(Integer id, Class<T> entityClass) {
        String sql = "select * from " + entityClass.getAnnotation(Entity.class).name()
                + " where id = " + id;

        T result = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            result = Objects.requireNonNull(getEntitiesList(entityClass)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public List<T> listEntitiesById(Integer id, Page page, Class<T> entityClass) {
        StringBuilder sql = new StringBuilder("select * from "
                + entityClass.getAnnotation(Entity.class).name()
                + " where id like '%" + id + "%'"
                + page.getLimit()
                + page.getSort());

        List<T> result = null;

        try {
            resultSet = connection.prepareStatement(sql.toString()).executeQuery();
            result = getEntitiesList(entityClass);

            resultSet = connection.prepareStatement("select count(*) total from "
                    + entityClass.getAnnotation(Entity.class).name())
                    .executeQuery();

            if (resultSet.next()) {

                page.setTotal(resultSet.getLong("total"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<T> listEntities(Page page, Class<T> entityClass) {
        StringBuilder sql = new StringBuilder("select * from "
                + entityClass.getAnnotation(Entity.class).name()
                + page.getSort()
                + page.getLimitAndOffset());

        List<T> result = null;

        try {
            System.out.println(sql);
            resultSet = connection.prepareStatement(sql.toString()).executeQuery();
            result = getEntitiesList(entityClass);

            resultSet = connection.prepareStatement("select count(*) total from "
                    + entityClass.getAnnotation(Entity.class).name())
                    .executeQuery();
            if (resultSet.next() ) {
                System.out.println(resultSet.getObject("total"));
                Long integer = (Long) resultSet.getObject("total");
                page.setTotal(integer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

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
            result = getEntitiesList((Class<T>) entity.getClass());
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
            result = connection.prepareStatement(sql).executeUpdate() > 0;
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
            String fieldName;
            Field[] fields = tClass.getDeclaredFields();
            // field的包装类 如：age为Integer
            // 保存method执行后返回的数据
            Object fieldValue;

            fieldsStr = new StringBuilder();
            condition = new StringBuilder(" where 1 = 1");
            for (Field field : fields) {
                field.setAccessible(true);

                fieldValue = field.get(entity);
                fieldName = field.getName();


                fieldsStr.append(fieldName).append(", ");
                if (fieldValue != null) {
                    if (field.getType() == String.class) {
                        condition.append(" and ").append(fieldName).append(" = '").append(fieldValue).append("'");
                    } else {
                        condition.append(" and ").append(fieldName).append(" = ").append(fieldValue);
                    }
                }
            }
            fieldsStr.delete(fieldsStr.length() - 2, fieldsStr.length());

            result = "select " + fieldsStr + " from "
                    + tClass.getAnnotation(Entity.class).name() + condition;
        } catch (IllegalAccessException e) {
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
                field.setAccessible(true);

                if (!field.isAnnotationPresent(GeneratedValue.class)) {
                    fieldsStr.append(field.getName()).append(", ");
                    fieldsValueStr.append("'").append(field.get(entity)).append("', ");
                }
            }
            fieldsStr.delete(fieldsStr.length() - 2, fieldsStr.length());
            fieldsValueStr.delete(fieldsValueStr.length() - 2, fieldsValueStr.length());

            result = "insert ignore into " + tClass.getAnnotation(Entity.class).name()
                    + "(" + fieldsStr + ") values (" + fieldsValueStr + ")";

        } catch (IllegalAccessException e) {
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
                + tClass.getAnnotation(Entity.class).name()
                + " where ");

        for (Field field : fields) {
            if (field.isAnnotationPresent(GeneratedValue.class)) {
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
                field.setAccessible(true);
                String fieldName = field.getName();

                if (field.isAnnotationPresent(GeneratedValue.class)) {
                    // example: id = 3;
                    condition.append(field.getName()).append(" = ").append(field.get(entity));
                } else {
                    // example: name = xx, age = 20,
                    sql.append(fieldName).append(" = '").append(field.get(entity)).append("', ");
                }
            }

            sql.delete(sql.length() - 2, sql.length());
            sql.append(condition);

            result = "update " + tClass.getAnnotation(Entity.class).name() + " set " + sql.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<T> getEntitiesList(Class<T> tClass) {
        if (resultSet == null) {
            return null;
        }

        // 从数据库获取到的数据
        List<T> result = null;
        Field[] fields;
        T t;

        try {
            fields = tClass.getDeclaredFields();
            result = new ArrayList<>();

            while (resultSet.next()) {
                t = tClass.newInstance();

                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(t, resultSet.getObject(field.getName()));
                }

                result.add(t);
            }

        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return result;
    }
}
