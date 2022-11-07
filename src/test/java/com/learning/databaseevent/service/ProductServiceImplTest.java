package com.learning.databaseevent.service;

import com.learning.databaseevent.repository.ProductRepo;
import com.learning.databaseevent.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
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
    public void testTwoGetProductsCalls () {
        when (productRepo.findByPrId(ArgumentMatchers.anyLong())).thenReturn(Optional.of(productEntity), Optional.empty());

        ProductEntity productEntity1 = productService.getProduct(ArgumentMatchers.anyLong());
        assertNotNull(productEntity1, "After inserting Product, Product cannot be null");
        assertThat(productEntity1.getPrId(), is (42700L));

        ProductEntity productEntity2 = productService.getProduct(ArgumentMatchers.anyLong());
        assertThat (productEntity1, sameInstance(productEntity2));

        verify(productRepo, times(2)).findByPrId(ArgumentMatchers.anyLong());
    }
}
