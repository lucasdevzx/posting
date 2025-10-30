package com.posting.post.resources;

import java.util.List;

import com.posting.post.dto.response.CategoryResponseDTO;
import com.posting.post.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.posting.post.entities.Category;
import com.posting.post.services.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<Category> categories = categoryService.findAll(page, size);
        return ResponseEntity.ok().body(categories.map(categoryMapper::toCategory));
    }
}
