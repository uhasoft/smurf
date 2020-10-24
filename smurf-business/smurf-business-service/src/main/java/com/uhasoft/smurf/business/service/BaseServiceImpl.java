package com.uhasoft.smurf.business.service;

import com.uhasoft.smurf.business.dao.BaseDao;
import com.uhasoft.smurf.business.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Weihua
 * @since 1.0.0
 */
public abstract class BaseServiceImpl<T extends BaseEntity, M extends BaseDao<T>> implements BaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    protected M dao;

    public BaseServiceImpl(M dao){
        this.dao = dao;
    }

    @Override
    public T findById(String id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findAll() {
        return dao.selectAll();
    }

    @Override
    public void delete(String id) {
        T t = dao.selectByPrimaryKey(id);
        t.setDeleted(true);
        save(t);
    }

    @Override
    public T save(T entity) {
        int count;
        if(entity.getId() == null){
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedAt(new Date());
            entity.setLastModifiedAt(new Date());
            entity.setCreatedBy(entity.getLastModifiedBy());
            entity.setDeleted(false);
            entity.setVersion(0);
            count = dao.insert(entity);
        } else {
            entity.setLastModifiedAt(new Date());
            entity.setVersion(entity.getVersion() + 1);
            count = dao.updateByPrimaryKey(entity);
        }
        if(count == 1){
            logger.debug("Successfully saved record: {}", entity);
        } else {
            logger.error("Failed to save record: {}", entity);
        }
        return entity;
    }
}
