package com.example.springrestwebflux.controllers;

import com.example.springrestwebflux.domain.Category;
import com.example.springrestwebflux.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
}
