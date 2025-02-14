package com.projectRestAPI.MyShop.service;

import com.projectRestAPI.MyShop.dto.request.SearchCriteria;
import com.projectRestAPI.MyShop.dto.response.ResponseObject;
import com.projectRestAPI.MyShop.model.BaseEntity;
import com.projectRestAPI.MyShop.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseEntity,ID extends Serializable> {
    ResponseEntity<ResponseObject> createNew(E entity);

    ResponseEntity<ResponseObject> update(E entity);
    ResponseEntity<ResponseObject> delete(ID id);

    Optional<E> findById(ID id);

    Boolean existsById(ID id);
    List<E> findAll();

    Page<E> getAll(List<SearchCriteria> params, Pageable pageable,List<String> sort, Users users);
}
