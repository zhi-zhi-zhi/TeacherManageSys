package com.cqut.icode.services.impl;

import com.cqut.icode.annotation.AutoWired;
import com.cqut.icode.dao.EntityDao;
import com.cqut.icode.entities.Page;
import com.cqut.icode.entities.base.BaseEntity;
import com.cqut.icode.services.EntityService;

import java.util.List;

/**
 * @author 谭强
 * @date 2019/6/7
 */
public class EntityServiceImpl<T extends BaseEntity> implements EntityService<T> {
    @AutoWired
    private static EntityDao dao;

    @Override
    public T getEntityById(Integer id, Class<T> entityClass) {
        return (T) dao.getEntityById(id, entityClass);
    }

    @Override
    public List<T> listEntitiesById(Integer id, Page page, Class<T> entityClass) {
        return dao.listEntitiesById(id, page, entityClass);
    }

    @Override
    public T getEntityByEntity(T entity, Class<T> entityClass) {
        return null;
    }

    @Override
    public List<T> listEntities(Page page, Class<T> entityClass) {
        return dao.listEntities(page, entityClass);
    }

    @Override
    public boolean saveEntity(T entity) {
        return dao.saveEntity(entity);
    }

    @Override
    public boolean updateEntity(T entity) {
        return dao.updateEntity(entity);
    }

    @Override
    public boolean removeEntities(List<Long> ids, Class<T> entityClass) {
        return dao.removeEntities(ids, entityClass);
    }
}
