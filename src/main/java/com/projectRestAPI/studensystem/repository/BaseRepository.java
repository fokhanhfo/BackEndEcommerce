package com.projectRestAPI.studensystem.repository;

import com.projectRestAPI.studensystem.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends  JpaRepository<E,ID>,
        JpaSpecificationExecutor<E>
{

}
