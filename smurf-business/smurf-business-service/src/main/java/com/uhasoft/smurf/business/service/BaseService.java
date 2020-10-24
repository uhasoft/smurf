package com.uhasoft.smurf.business.service;

import com.uhasoft.smurf.business.entity.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface BaseService<T extends BaseEntity> {


    T findById(String id);

    List<T> findAll();

    void delete(String id);

    T save(T entity);

    /**
     * 默认实现类的第一个泛型是模型类
     * BaseService的泛型顺序并不代表实现类的顺序就一定是这个
     * @return 模型类
     */
    default Class<T> getEntityClass()  {
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            return (Class<T>)p[0];
        }
        return null;
    }

    /**
     * 获取持久化模型类名
     * @return 类名
     */
    default String getEntityName(){
        Class clazz = getEntityClass();
        return clazz.getSimpleName();
    }

}
