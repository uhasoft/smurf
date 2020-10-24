package com.uhasoft.smurf.business.web;

import com.uhasoft.smurf.business.entity.BaseEntity;
import com.uhasoft.smurf.business.service.BaseService;
import com.uhasoft.smurf.common.model.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class BaseController<T extends BaseEntity, S extends BaseService<T>> {

    protected S service;

    public BaseController(S service){
        this.service = service;
    }

    @GetMapping
    public Response<List<T>> findAll(){
        return Response.success(service.findAll());
    }

    @GetMapping("{id}")
    public Response<T> findById(@PathVariable String id){
        return Response.success(service.findById(id));
    }

    @PostMapping
    public Response<T> save(@RequestBody T entity){
        return Response.success(service.save(entity));
    }

    @DeleteMapping("{id}")
    public Response<T> delete(@PathVariable String id){
        service.delete(id);
        return Response.success(null);
    }
}
