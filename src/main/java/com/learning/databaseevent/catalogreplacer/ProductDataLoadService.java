package com.learning.databaseevent.catalogreplacer;

import com.learning.databaseevent.repository.ProductRepo;
import com.learning.databaseevent.repository.entity.ProductEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class ProductDataLoadService {

    private ProductRepo productRepo;
    private ProductDbJSonParser productDbJSonParser;
    private ModelMapper modelMapper;

    public ProductDataLoadService(ProductDbJSonParser productDbJSonParser, ProductRepo productRepo) {
        this.productRepo = productRepo;
        this.productDbJSonParser = productDbJSonParser;
        modelMapper = new ModelMapper();
    }

    public void loadDatabase () {
        try{
            List<Product> products = productDbJSonParser.parseProductDBJson();
            if (! CollectionUtils.isEmpty(products)){
                log.info("total products for loading : {}", products.size());
                for (Product product : products){
                    log.info("each product to be loaded {} : ", product);
                    ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
                    log.info("each Product Entity from product data object {} : ", productEntity);
                    productRepo.save(productEntity);
                }
            }
        } catch (Exception exception){
            log.error("Encountered exception while loading the products", exception);
        }
    }
}
