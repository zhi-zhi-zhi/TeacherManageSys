package com.cqut.icode.dao;

import com.cqut.icode.entities.base.BaseEntity;

import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/16
 */
public interface EntityDao<T extends BaseEntity> {

    /**
     * 根据条件获取一个对象
     *
     * @param entity 条件组成的实体
     * @return 数据库存在该对象则返回，否则返回null
     */
    T getEntity(T entity);

    /**
     * 从数据库中获取该实体的所有元祖
     *
     * @param entity ·
     * @return 实体对象集合
     */
    List<T> listEntities(T entity);

    /**
     * 在数据库中插入该对象
     *
     * @param entity 对象拼接好的sql条件，例如 (id=x, name='x')
     * @return 插入成功为true
     */
    boolean saveEntity(T entity);

    /**
     * 在数据库中删除该对象对应的元祖
     *
     * @param ids    主键拼接好的sql条件，如 (1, 2, 3)
     * @param tClass 实体类的类对象，通过反射获取相应信息
     * @return 删除成功为true
     */
    boolean removeEntities(List<Long> ids, Class<T> tClass);

    /**
     * 在数据库中修改该对象对应的元祖
     *
     * @param entity 修改的信息
     * @return 修改成功为true
     */
    boolean updateEntity(T entity);
}
