package com.nhnacademy.marketgg.server.repository.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.nhnacademy.marketgg.server.dto.request.CategorizationCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.entity.Product;
import com.nhnacademy.marketgg.server.repository.asset.AssetRepository;
import com.nhnacademy.marketgg.server.repository.categorization.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.category.CategoryRepository;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorizationRepository categorizationRepository;

    @Autowired
    private AssetRepository assetRepository;

    private Product product;
    private ProductCreateRequest productRequest;
    private Asset asset;
    private Category category;
    private Categorization categorization;

    @BeforeEach
    void setUp() {
        productRequest = new ProductCreateRequest();
        ReflectionTestUtils.setField(productRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(productRequest, "name", "??????");
        ReflectionTestUtils.setField(productRequest, "content", "????????? ?????? ??????");
        ReflectionTestUtils.setField(productRequest, "totalStock", 100L);
        ReflectionTestUtils.setField(productRequest, "price", 2000L);
        ReflectionTestUtils.setField(productRequest, "description", "???????????? ??????");
        ReflectionTestUtils.setField(productRequest, "unit", "1??????");
        ReflectionTestUtils.setField(productRequest, "deliveryType", "????????????");
        ReflectionTestUtils.setField(productRequest, "origin", "???????????????");
        ReflectionTestUtils.setField(productRequest, "packageType", "??????");
        ReflectionTestUtils.setField(productRequest, "allergyInfo", "???????????????");
        ReflectionTestUtils.setField(productRequest, "capacity", "100");
        ReflectionTestUtils.setField(productRequest, "expirationDate", LocalDate.now());

        asset = Asset.create();
        // ReflectionTestUtils.setField(asset, "id", 1L);

        CategorizationCreateRequest categorizationRequest = new CategorizationCreateRequest();

        ReflectionTestUtils.setField(categorizationRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categorizationRequest, "name", "??????");
        ReflectionTestUtils.setField(categorizationRequest, "alias", "Products");

        categorization = new Categorization(categorizationRequest);

        CategoryCreateRequest categoryRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "100");
        ReflectionTestUtils.setField(categoryRequest, "name", "??????");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);

        category = new Category(categoryRequest, categorization);
        assetRepository.save(asset);

        categorizationRepository.save(categorization);
        categoryRepository.save(category);
    }

    @Test
    @DisplayName("?????? ????????? ????????? Page??? ????????? return????????? ?????????")
    void testFindAllProducts() {
        IntStream.rangeClosed(1, 10)
                 .forEach(i -> {
                     product = new Product(productRequest, asset, category);
                     productRepository.save(product);
                 });

        assertThat(productRepository.findAllProducts(PageRequest.of(0,10))).hasSize(10);
    }

    @Test
    @DisplayName("???????????? ????????? ?????? ??? ????????? ?????????")
    void testQueryById() {
        product = new Product(productRequest, asset, category);
        productRepository.save(product);

        assertThat(productRepository.queryById(product.getId()).getName()).isEqualTo(product.getName());
    }

    @Test
    @DisplayName("?????? ????????? ?????? ????????? ????????? ??????, ?????? ????????? ?????? ??? ????????? ?????????")
    void testFindByNameContaining() {
        product = new Product(productRequest, asset, category);
        productRepository.save(product);

        assertThat(productRepository.findByNameContaining("??????")).hasSize(1);
    }

    @Test
    @DisplayName("??????????????? ????????? ?????? ??? ????????? ?????????")
    void testFindByCategoryCode() {
        IntStream.rangeClosed(1, 10)
                 .forEach(i -> {
                     product = new Product(productRequest, asset, category);
                     productRepository.save(product);
                 });

        assertThat(productRepository.findByCategoryCode("001")).hasSize(10);
    }
}
