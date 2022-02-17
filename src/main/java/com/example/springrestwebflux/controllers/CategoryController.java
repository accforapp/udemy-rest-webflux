package com.example.springrestwebflux.controllers;

import com.example.springrestwebflux.domain.Category;
import com.example.springrestwebflux.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class CategoryController {

  private final CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping("/api/v1/categories")
  public Flux<Category> listOfCategories() {

    return categoryRepository.findAll();
  }

  @GetMapping("/api/v1/categories/{id}")
  public Mono<Category> getCategoryById(@PathVariable String id) {

    return categoryRepository.findById(id);
  }

  @PostMapping("/api/v1/categories")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createNewCategory(@RequestBody Publisher<Category> category) {

    return categoryRepository.saveAll(category).then();
  }

  @PutMapping("/api/v1/categories/{id}")
  public Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {

    category.setId(id);

    return categoryRepository.save(category);
  }

  @PatchMapping("/api/v1/categories/{id}")
  public Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {

    Category categoryById = categoryRepository.findById(id).block();

    if (!Objects.equals(categoryById.getDescription(), category.getDescription())) {
      categoryById.setDescription(category.getDescription());
      return categoryRepository.save(categoryById);
    }

    return Mono.just(categoryById);
  }
}
