package com.cqut.icode.services;

import com.cqut.icode.entities.Page;
import com.cqut.icode.entities.base.BaseEntity;

import java.util.List;

/**
 * @author 谭强
 * @date 2019/6/7
 */
public interface EntityService <T extends BaseEntity>{
    /**
     * @param id
     * @param entityClass
     * @return
     */
    T getEntityById(Integer id, Class<T> entityClass);

    T getEntityByEntity(T entity, Class<T> entityClass);

    /**
     * @param page id模糊匹配，
     * @param entityClass
     * @return
     */
    List<T> listEntitiesById(Integer id, Page page, Class<T> entityClass);

    List<T> listEntities(Page page, Class<T> entityClass);

    boolean saveEntity(T entity);

    boolean removeEntities(List<Long> ids, Class<T> entityClass);

    boolean updateEntity(T entity);
}
