package com.learning.databaseevent.service;

import com.learning.databaseevent.dataobject.Product;
import com.learning.databaseevent.utils.ProductDbJSonParser;
import com.learning.databaseevent.repository.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductDataLoadService {

    private ProductService productService;
    private ProductDbJSonParser productDbJSonParser;
    private ModelMapper modelMapper;

    public ProductDataLoadService(ProductDbJSonParser productDbJSonParser, ProductService productService) {
        this.productService = productService;
        this.productDbJSonParser = productDbJSonParser;
        modelMapper = new ModelMapper();

        TypeMap<Product, ProductEntity> typeMap = modelMapper.createTypeMap(Product.class, ProductEntity.class);
        typeMap.addMapping(Product::getPrId, ProductEntity::setPrId);
    }

    @Transactional
    public void loadDatabase () {
        try{
            List<Product> products = productDbJSonParser.parseProductDBJson();
            List<ProductEntity> productEntities = new ArrayList<>(products.size());
            if (! CollectionUtils.isEmpty(products)){
                log.info("total products for loading : {}", products.size());
                for (Product product : products){
                    log.info("each product to be loaded {} : ", product);
                    ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
                    productEntity.setCreatedDateTime(LocalDateTime.now().plusMinutes(2));
                    log.info("each Product Entity from product data object {} : ", productEntity);
                    productEntities.add(productEntity);
//                    productService.saveProduct(productEntity);
                }
                productService.saveAll(productEntities);
            }
        } catch (Exception exception){
            log.error("Encountered exception while loading the products", exception);
        }
    }
}
