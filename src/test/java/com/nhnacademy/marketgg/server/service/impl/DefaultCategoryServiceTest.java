package com.nhnacademy.marketgg.server.service.impl;

import com.nhnacademy.marketgg.server.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.server.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.server.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.server.entity.Categorization;
import com.nhnacademy.marketgg.server.entity.Category;
import com.nhnacademy.marketgg.server.exception.CategorizationNotFoundException;
import com.nhnacademy.marketgg.server.exception.CategoryNotFoundException;
import com.nhnacademy.marketgg.server.repository.CategorizationRepository;
import com.nhnacademy.marketgg.server.repository.CategoryRepository;
import com.nhnacademy.marketgg.server.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class DefaultCategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CategorizationRepository categorizationRepository;

    @Test
    @DisplayName("카테고리 생성 성공")
    void testCreateCategorySuccess() {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryCreateRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryCreateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryCreateRequest, "sequence", 1);
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.of(new Categorization("111", "상품", "product")));

        categoryService.createCategory(categoryCreateRequest);

        verify(categorizationRepository, times(1)).findById(anyString());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 생성 실패")
    void testCreateCategoryFail() {
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest();
        ReflectionTestUtils.setField(categoryCreateRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryCreateRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryCreateRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryCreateRequest, "sequence", 1);
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(categoryCreateRequest))
                .isInstanceOf(CategorizationNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    void testRetrieveCategories() {
        when(categoryRepository.findAllCategories())
                .thenReturn(List.of(new CategoryRetrieveResponse()));

        List<CategoryRetrieveResponse> categoryResponses = categoryService.retrieveCategories();

        assertThat(categoryResponses).hasSize(1);
    }

    @Test
    @DisplayName("카테고리 수정 성공")
    void testUpdateCategorySuccess() {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.of(new Category("001", null, "과일", 2)));
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.of(new Categorization("001", "상품", "product")));

        categoryService.updateCategory("001", categoryRequest);
    }

    @Test
    @DisplayName("카테고리 수정 실패(카테고리 존재 X)")
    void testUpdateCategoryFailWhenNoCategory() {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.empty());
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.of(new Categorization("001", "상품", "product")));

        assertThatThrownBy(() -> categoryService.updateCategory("001", categoryRequest))
                .isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 수정 실패(카테고리 분류 존재 X)")
    void testUpdateCategoryFailWhenNoCategorization() {
        CategoryUpdateRequest categoryRequest = new CategoryUpdateRequest();
        ReflectionTestUtils.setField(categoryRequest, "categorizationCode", "001");
        ReflectionTestUtils.setField(categoryRequest, "name", "채소");
        ReflectionTestUtils.setField(categoryRequest, "sequence", 1);
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.of(new Category("001", null, "과일", 2)));
        when(categorizationRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory("001", categoryRequest))
                .isInstanceOf(CategorizationNotFoundException.class);
    }

    @Test
    @DisplayName("카테고리 삭제 성공")
    void testDeleteCategory() {
        when(categoryRepository.findById(anyString()))
                .thenReturn(Optional.of(new Category("001001", null, "친환경", 1)));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory("001001");

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 (삭제할 카테고리 존재 X)")
    void testDeleteCategoryFailWhenNotExistsCategory() {
        assertThatThrownBy(() -> categoryService.deleteCategory("99999"))
                .isInstanceOf(CategoryNotFoundException.class);
    }

}
