package com.uhasoft.smurf.business.service;

import com.uhasoft.smurf.business.dao.NameDao;
import com.uhasoft.smurf.business.entity.NameEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public abstract class NameServiceImpl<T extends NameEntity, M extends NameDao<T>> extends BaseServiceImpl<T, M> implements NameService<T> {

    public NameServiceImpl(M dao) {
        super(dao);
    }

    @Override
    public List<T> findByName(String name) {
        Example example = new Example(getEntityClass());
        example.createCriteria().andEqualTo("name", name).andEqualTo("deleted", false);
        return dao.selectByExample(example);
    }
}
