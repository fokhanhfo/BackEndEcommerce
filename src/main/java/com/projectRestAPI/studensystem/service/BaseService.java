package com.projectRestAPI.studensystem.service;

import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

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
}
