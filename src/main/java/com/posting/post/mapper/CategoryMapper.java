package com.posting.post.mapper;

import com.posting.post.dto.response.CategoryResponseDTO;
import com.posting.post.dto.response.ComentResponseDTO;
import com.posting.post.entities.Category;
import com.posting.post.entities.Coment;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


    public CategoryResponseDTO toCategory(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }
}
