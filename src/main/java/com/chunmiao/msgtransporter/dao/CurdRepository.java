package com.chunmiao.msgtransporter.dao;

import com.chunmiao.msgtransporter.entity.msgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CurdRepository extends JpaRepository<msgEntity, Integer>, JpaSpecificationExecutor<msgEntity> {

    public msgEntity findTopByOrderByIdDesc();
}
