package com.posting.post.services;

import java.util.List;

import com.posting.post.dto.request.CategoryRequestDTO;
import com.posting.post.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Category;
import com.posting.post.repositories.CategoryRepository;

@Service
public class CategoryService  {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public Page<Category> findAll(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category createCategory(CategoryRequestDTO bodu) {
        var entity = categoryMapper.toEntity(bodu);
        return categoryRepository.save(entity);
    }
}
