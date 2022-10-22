package com.learning.databaseevent;

import com.learning.databaseevent.repository.ProductRepo;
import com.learning.databaseevent.repository.entity.ProductEntity;
import com.learning.databaseevent.service.ProductService;
import com.learning.databaseevent.service.ProductServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DatabaseEventApplicationTests {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductServiceImpl productService;

    private ProductEntity productEntity;

    @BeforeEach
    public void init () {
        productEntity = new ProductEntity();
        productEntity.setPrId(42700L);
        productEntity.setDpci("255140051");
        productEntity.setModel("6161B");
        productEntity.setName("test iphone");
        productEntity.setTcin("52880272");
        productEntity.setUpc("190198451781");
        productEntity.setProductId(UUID.randomUUID());
        productEntity.setCreatedDateTime(LocalDateTime.now().plusMinutes(2));


    }

    @Test
    public void testTwoGetProductCallsDatabaseOnlyOnce () {
        productRepo.saveAndFlush(productEntity);

        ProductEntity productEntity1 = productService.getProduct(42700L);
        assertNotNull(productEntity1, "After inserting Product, Product cannot be null");
        assertThat(productEntity1.getPrId(), is (42700L));

        ProductEntity productEntity2 = productService.getProduct(42700L);
        assertThat (productEntity1, is(not(same(productEntity2))));
        assertThat(productEntity1.getPrId(), is (42700L));
    }

    @Test
    public void testAddNewproductAndFetchTheSameIsOnlyOneDatabaseCall () {
        productRepo.saveAndFlush(productEntity);

        ProductEntity productEntity1 = productService.getProduct(42700L);
        assertNotNull(productEntity1, "After inserting Product, Product cannot be null");
        assertThat(productEntity1.getPrId(), is (42700L));

        ProductEntity productEntity2 = productService.getProduct(42700L);
        assertThat (productEntity1, is(not(same(productEntity2))));
        assertThat(productEntity1.getPrId(), is (42700L));
    }
}
