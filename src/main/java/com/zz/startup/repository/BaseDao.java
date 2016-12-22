package com.zz.startup.repository;

import com.zz.startup.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseDao<M extends BaseEntity, ID extends Serializable> extends JpaRepository<M, ID>, JpaSpecificationExecutor<M> {
}
