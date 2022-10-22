package com.learning.databaseevent.repository;

import com.learning.databaseevent.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, String>{
    Optional<ProductEntity> findByPrId (Long prId);
}


