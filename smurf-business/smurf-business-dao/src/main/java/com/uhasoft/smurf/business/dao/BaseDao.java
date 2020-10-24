package com.uhasoft.smurf.business.dao;

import com.uhasoft.smurf.business.entity.BaseEntity;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface BaseDao<T extends BaseEntity> extends Mapper<T> {
}
