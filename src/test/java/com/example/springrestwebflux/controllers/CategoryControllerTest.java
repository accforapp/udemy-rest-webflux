package com.example.springrestwebflux.controllers;

import com.example.springrestwebflux.domain.Category;
import com.example.springrestwebflux.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryControllerTest {

  WebTestClient webTestClient;
  CategoryRepository categoryRepository;
  CategoryController categoryController;

  @BeforeEach
  void setUp() {
    categoryRepository = Mockito.mock(CategoryRepository.class);
    categoryController = new CategoryController(categoryRepository);

    webTestClient = WebTestClient.bindToController(categoryController).build();
  }

  @Test
  void listOfCategories() {

    Category testCategory1 = Category.builder().id("1").description("TestCategory1").build();
    Category testCategory2 = Category.builder().id("2").description("TestCategory2").build();

    given(categoryRepository.findAll()).willReturn(Flux.just(testCategory1, testCategory2));

//    when(categoryRepository.findAll()).thenReturn(Flux.just(testCategory1, testCategory2));

    webTestClient.get().uri("/api/v1/categories")
        .exchange()
        .expectStatus().isOk()
        .expectBody().json("[{\"id\": \"1\", \"description\": \"TestCategory1\"}, {\"id\": \"2\", \"description\": \"TestCategory2\"}]");

  }

  @Test
  void getCategoryById() {

    Category testCategory = Category.builder().id("1").description("Test").build();

    given(categoryRepository.findById("1")).willReturn(Mono.just(testCategory));

    webTestClient.get().uri("/api/v1/categories/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody().jsonPath("$.id", "1");
  }

  @Test
  void createNewCategory() {

    given(categoryRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(new Category()));

    Mono<Category> categoryToSav = Mono.just(Category.builder().description("Test category").build());

    webTestClient.post().uri("/api/v1/categories")
        .body(categoryToSav, Category.class)
        .exchange()
        .expectStatus().isCreated();
  }

  @Test
  void updateCategory() {

    given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(Category.builder().id("1").description("Test category").build()));

    Mono<Category> categoryForUpdate = Mono.just(Category.builder().description("Test category").build());

    webTestClient.put().uri("/api/v1/categories/1")
        .body(categoryForUpdate, Category.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody().json("{\"id\": \"1\", \"description\": \"Test category\"}");
  }

  @Test
  void patchCategory() {

    given(categoryRepository.findById("1")).willReturn(Mono.just(Category.builder().id("1").build()));
    given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(Category.builder().id("1").description("Test category").build()));

    Mono<Category> categoryToUpdate = Mono.just(Category.builder().description("Test category").build());

    webTestClient.patch().uri("/api/v1/categories/1")
        .body(categoryToUpdate, Category.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody().json("{\"id\": \"1\", \"description\": \"Test category\"}");

    verify(categoryRepository).save(any(Category.class));
  }
}