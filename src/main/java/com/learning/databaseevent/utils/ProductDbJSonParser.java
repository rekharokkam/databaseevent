package com.learning.databaseevent.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.learning.databaseevent.dataobject.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class ProductDbJSonParser {

    public List<Product> parseProductDBJson () throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream= Product.class.getResourceAsStream("/productdb.json");
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Product.class);
        List<Product> products = mapper.readValue(inputStream, collectionType);

        if (CollectionUtils.isEmpty(products)){
            log.error("the products list is empty");
            throw new Exception("NO products found for loading");
        }
        return products;
    }
}
