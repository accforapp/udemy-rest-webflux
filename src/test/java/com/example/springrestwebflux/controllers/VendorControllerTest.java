package com.example.springrestwebflux.controllers;

import com.example.springrestwebflux.domain.Vendor;
import com.example.springrestwebflux.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

class VendorControllerTest {

  WebTestClient webClient;
  VendorRepository vendorRepository;
  VendorController vendorController;

  @BeforeEach
  void setUp() {
    vendorRepository = Mockito.mock(VendorRepository.class);
    vendorController = new VendorController(vendorRepository);

    webClient = WebTestClient.bindToController(vendorController).build();

  }

  @Test
  void getVendors() {

    Vendor first = Vendor.builder().id("1").firstName("First").lastName("Vendor").build();
    Vendor second = Vendor.builder().id("2").firstName("Second").lastName("Vendor").build();

    when(vendorRepository.findAll()).thenReturn(Flux.just(first, second));

    webClient.get().uri("/api/v1/vendors")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .json("[{\"id\": \"1\", \"firstName\": \"First\", \"lastName\": \"Vendor\"}, " +
        "{\"id\": \"2\", \"firstName\": \"Second\", \"lastName\": \"Vendor\"}]");
  }

  @Test
  void getVendorById() {

    Vendor first = Vendor.builder().id("1").firstName("First").lastName("Vendor").build();

    when(vendorRepository.findById("1")).thenReturn(Mono.just(first));

    webClient.get().uri("/api/v1/vendors/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .json("{\"id\": \"1\", \"firstName\": \"First\", \"lastName\": \"Vendor\"}");
  }
}