package com.learning.databaseevent.repository;

import com.learning.databaseevent.repository.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
Annotation for a JPA test that focuses only on JPA components.
Using this annotation will disable full auto-configuration and instead apply only configuration relevant to JPA tests.
By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.
They also use an embedded in-memory database (replacing any explicit or usually auto-configured DataSource).
The @AutoConfigureTestDatabase annotation can be used to override these settings.
SQL queries are logged by default by setting the spring.jpa.show-sql property to true.
This can be disabled using the showSql attribute.
If you are looking to load your full application configuration, but use an embedded database,
you should consider @SpringBootTest combined with @AutoConfigureTestDatabase rather than this annotation.
When using JUnit 4, this annotation should be used in combination with @RunWith(SpringRunner.class).
 */

@DataJpaTest
@ComponentScan("com.learning.databaseevent")
//@Transactional // after each test data roll back happens automatically. This is a redundant annotation
public class ProductRepositoryTest {

//    @Autowired //Entity classes cannot be autowired
    private ProductEntity productEntity;
    private List<ProductEntity> productEntities;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Autowiring attribute in test is normal. In a class we auto-wire using a constructor.
    @Autowired
//    @Qualifier("productRepo") //this is redundant. Just for demo purposes used here. Autowire by Type is by default.
    private ProductRepo productRepo;

    @BeforeEach
    public void init () {
        productEntity = new ProductEntity();
        productEntity.setPrId(42700L);
        productEntity.setDpci("255140051");
        productEntity.setModel("6161B");
        productEntity.setName("test iphone");
        productEntity.setTcin("52880272");
        productEntity.setUpc("190198451781");
        productEntity.setCreatedDateTime(LocalDateTime.now().plusHours(1));
    }

    @Test
    public void testCreateNewProduct () {
        ProductEntity persistedProductEntity = productRepo.save(productEntity);
        System.out.println("persisted entity : " + persistedProductEntity);
        assertNotNull(persistedProductEntity, "After inserting Product, Product cannot be null");
        assertNotNull(persistedProductEntity.getProductId(), "productId cannot be null");
        assertEquals(productEntity.getTcin(), "52880272");
    }

    @Test
    public void testUpdateNewProduct () {
        productEntity.setTcin("52880234");
        ProductEntity persistedProductEntity = productRepo.save(productEntity);
        assertNotNull(persistedProductEntity, "After Updating product, Product cannot be null");
        assertEquals(productEntity.getTcin(), "52880234");
    }

    @Test
    public void testFindProduct () {
        Optional<ProductEntity> persistedProductEntity = productRepo.findByPrId(42700L);
        assertNotNull(persistedProductEntity, "After Updating product, Product cannot be null");
        assertEquals(productEntity.getTcin(), "52880272");
    }



//    @Test// this test did not work for me
//    public void testGetProductById () {
//        jdbcTemplate.query ("select productId from product", (rs, rowNum) -> rs.getString("productId") )
//                .forEach(eachProductId -> {
//                    ProductEntity productEntity1 = productRepo.findOne(eachProductId);
//                    assertNotNull(productEntity1);
//                    assertEquals(eachProductId, productEntity1.getProductId());
//                });
//    }

    @Test
    public void testFindAll () throws Exception {
        List<Long> legacyPrIds = productRepo.findAll().stream().map(ProductEntity::getPrId).collect(Collectors.toList());
        assertThat (legacyPrIds, containsInAnyOrder(42658L, 42659L, 42660L, 42664L, 42665L, 42666L, 42670L, 42671L, 42672L));
    }

    @Test
    public void testSaveAllProductsDuringInitialLoad(){
        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setPrId(42900L);
        productEntity1.setDpci("255140051");
        productEntity1.setModel("6161B");
        productEntity1.setName("test iphone");
        productEntity1.setTcin("52880272");
        productEntity1.setUpc("190198451781");
        productEntity1.setCreatedDateTime(LocalDateTime.now().plusHours(1));

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setPrId(42800L);
        productEntity2.setDpci("255140051");
        productEntity2.setModel("6161B");
        productEntity2.setName("test iphone");
        productEntity2.setTcin("52880272");
        productEntity2.setUpc("190198451781");
        productEntity2.setCreatedDateTime(LocalDateTime.now().plusHours(1));

        productEntities = Arrays.asList(productEntity, productEntity1, productEntity2);
        productRepo.saveAllAndFlush(productEntities);

        List<UUID> productIds = productEntities.stream().map(ProductEntity::getProductId).collect(Collectors.toList());
        List<Long> prIds = productEntities.stream().map(ProductEntity::getPrId).collect(Collectors.toList());

        assertThat(productIds, iterableWithSize(3));
        assertThat(productIds, everyItem(instanceOf(UUID.class)));
        assertThat(productEntities, everyItem(hasProperty("tcin")));

        assertThat(prIds, iterableWithSize(3));
        assertThat(prIds, hasItems(42700L, 42900L));

        assertThat(productEntities, hasItem(productEntity2));
    }
}
