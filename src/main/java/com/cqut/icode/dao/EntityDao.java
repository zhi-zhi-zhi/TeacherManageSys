package com.cqut.icode.dao;

import com.cqut.icode.entities.base.BaseEntity;
import java.util.List;

/**
 * @author 谭强
 * @date 2019/5/16
 */
public interface EntityDao<T extends BaseEntity> {

    /**
     * 从数据库中获取该实体的所有元祖
     * @param tClass 实体类的类对象，通过反射获取相应信息
     * @return 实体对象集合
     */
    public List<T> listEntities(String Condition, Class<T> tClass);

    /**
     * 在数据库中插入该对象
     * @param entity 对象拼接好的sql条件，例如 (id=x, name='x')
     * @return 插入成功为true
     */
    public boolean saveEntity(T entity);

    /**
     * 在数据库中删除该对象对应的元祖
     * @param condition 主键拼接好的sql条件，如 (1, 2, 3)
     * @param tClass 实体类的类对象，通过反射获取相应信息
     * @return 删除成功为true
     */
    public boolean removeEntities(String condition, Class<T> tClass);

    /**
     * 在数据库中修改该对象对应的元祖
     * @param entity 修改的信息
     * @param tClass 实体类的类对象，通过反射获取相应信息
     * @return 修改成功为true
     */
    public boolean updateEntity(T entity, Class<T> tClass);
}
