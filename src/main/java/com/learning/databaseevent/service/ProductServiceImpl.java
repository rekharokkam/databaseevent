package com.learning.databaseevent.service;

import com.learning.databaseevent.repository.ProductRepo;
import com.learning.databaseevent.repository.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepo productRepo;

    public ProductServiceImpl (ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @Override
    @Transactional (readOnly = true)
    @Cacheable("product-cache")
    public ProductEntity getProduct(Long prId) {
        return productRepo.findByPrId(prId).orElse(new ProductEntity());
    }

    @Override
    @Transactional
    public List<ProductEntity> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    @Transactional
    @CachePut("product-cache")
    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepo.saveAndFlush(productEntity);
    }

    @Override
    @Transactional
    @CacheEvict("product-cache")
    public void deleteProduct(ProductEntity productEntity) {
        productRepo.delete(productEntity);
    }

    @Override
    public List<ProductEntity> saveAll(List<ProductEntity> productEntities) {
        return productRepo.saveAllAndFlush(productEntities);
    }
}
