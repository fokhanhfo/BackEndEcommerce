package com.projectRestAPI.studensystem.service.Impl;

import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import com.projectRestAPI.studensystem.model.BaseEntity;
import com.projectRestAPI.studensystem.repository.BaseRepository;
import com.projectRestAPI.studensystem.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<E extends BaseEntity,ID extends Serializable,R extends BaseRepository<E,ID>> implements BaseService<E,ID> {
    protected R repository;

    @Autowired
    public void setRepository(R repository) {
        this.repository = repository;
    }
    @Override
    public ResponseEntity<ResponseObject> createNew(E entity) {
        repository.save(entity);
        return new ResponseEntity<>(new ResponseObject("success", "Thêm Mới Thành Công", 0, entity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseObject> update(E entity) {
        if (entity != null) {
            repository.save(entity);
            return new ResponseEntity<>(new ResponseObject("success", "Cập Nhật Thành Công", 0, entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObject("error", "Đối tượng không hợp lệ", 1, null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<ResponseObject> delete(ID id) {
        Optional<E> otp = findById(id);
        if (otp.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject("error", "Không Tìm Thấy ID",1,null), HttpStatus.BAD_REQUEST);
        }

        repository.deleteById(id);
        return new ResponseEntity<>(new ResponseObject("success", "Đã Xóa Thành Công", 0, otp.get()), HttpStatus.OK);
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Boolean existsById(ID id) {
        return null;
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }


}
