package com.learning.databaseevent.service;

import com.learning.databaseevent.repository.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    ProductEntity getProduct (Long prId);

    List<ProductEntity> getAllProducts ();

    ProductEntity saveProduct (ProductEntity productEntity);

    List<ProductEntity> saveAll (List<ProductEntity> productEntities);

    void deleteProduct (ProductEntity productEntity);
}
