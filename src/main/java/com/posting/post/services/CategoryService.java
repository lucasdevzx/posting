package com.posting.post.services;

import java.util.List;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.dto.request.CategoryRequestDTO;
import com.posting.post.mapper.CategoryMapper;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.posting.post.entities.Category;
import com.posting.post.repositories.CategoryRepository;

@Service
public class CategoryService  {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final AuthenticatedUserService authenticatedUserService;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper,
                           AuthenticatedUserService authenticatedUserService) {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.authenticatedUserService = authenticatedUserService;
    }

    public Page<Category> findAll(int page, int size) {
        return categoryRepository.findAll(PageRequest.of(page, size));
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PreAuthorize("isAuthenticated()")
    public Category createCategory(CategoryRequestDTO body) {

        // Regra de negócio
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        if (!isAdmin) {
            throw new UnauthorizedActionException("Only admins can create categories.");
        }

        var entity = categoryMapper.toEntity(body);
        return categoryRepository.save(entity);
    }

    @PreAuthorize("isAuthenticated()")
    public Category updateCategory(Long categoryId, CategoryRequestDTO body) {

        // Regra de negócio
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        if (!isAdmin) {
            throw new UnauthorizedActionException("Only admins can update categories.");
        }

        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new UnauthorizedActionException(categoryId);
        }

        var entity = categoryRepository.getReferenceById(categoryId);
        var obj = categoryMapper.toEntity(body);
        updateData(entity, obj);
        return categoryRepository.save(entity);
    }

    // Auxílio update
    public void updateData(Category entity, Category obj) {
        entity.setName(obj.getName());
    }

    @PreAuthorize("isAuthenticated()")
    public void deleteCategory(Long categoryId) {

        // Regra de negócio
        boolean isAdmin = authenticatedUserService.hasRole("ADMIN");
        if (!isAdmin) {
            throw new UnauthorizedActionException("Only admins can delete categories.");
        }

        categoryRepository.deleteById(categoryId);
    }
}