package com.uhasoft.smurf.business.service;

import com.uhasoft.smurf.business.entity.NameEntity;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface NameService<T extends NameEntity> extends BaseService<T> {

    List<T> findByName(String name);
}
