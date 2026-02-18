package com.posting.post.resources;

import java.net.URI;
import java.util.List;

import com.posting.post.dto.request.CategoryRequestDTO;
import com.posting.post.dto.response.CategoryResponseDTO;
import com.posting.post.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.posting.post.entities.Category;
import com.posting.post.services.CategoryService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Categorias")
public class CategoryResource {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategoryResource(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    @Operation(summary = "Busca todas categorias", description = "Retorna categorias em paginação")
    public ResponseEntity<Page<CategoryResponseDTO>> findAll(@RequestParam int page, @RequestParam int size) {
        Page<Category> categories = categoryService.findAll(page, size);
        return ResponseEntity.ok().body(categories.map(categoryMapper::toCategory));
    }

    @GetMapping(value = "/{categoryId}")
    @Operation(summary = "Busca uma categoria por Id", description = "Retorna uma única categoria . Apenas para Admins")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryMapper.toCategory(categoryService.findById(categoryId)));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova categoria", description = "Retorna uma categoria. Apenas para Admins")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody
                                                                  @Valid
                                                                  CategoryRequestDTO body) {
        var entity = categoryService.createCategory(body);
        URI uri = ServletUriComponentsBuilder.fromPath("/{categoryId}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryMapper.toCategory(entity));
    }

    @PutMapping(value = "/{categoryId}")
    @Operation(summary = "Atualiza uma categoria por Id", description = "Retorna uma categoria. Apenas para Admins")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long categoryId,
                                                              @Valid
                                                              @RequestBody CategoryRequestDTO body) {

        return ResponseEntity.ok().body(categoryMapper.toCategory(categoryService.updateCategory(categoryId, body)));
    }

    @DeleteMapping(value = "/{categoryId}")
    @Operation(summary = "Deleta uma categoria por Id", description = "Retorna vazio. Apenas para Admins")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
