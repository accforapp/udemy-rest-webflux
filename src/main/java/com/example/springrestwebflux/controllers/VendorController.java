package com.example.springrestwebflux.controllers;

import com.example.springrestwebflux.domain.Vendor;
import com.example.springrestwebflux.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

  private final VendorRepository vendorRepository;

  public VendorController(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  @GetMapping("/api/v1/vendors")
  public Flux<Vendor> getVendors() {

    return vendorRepository.findAll();
  }

  @GetMapping("/api/v1/vendors/{id}")
  public Mono<Vendor> getVendorById(@PathVariable String id) {

    return vendorRepository.findById(id);
  }
}
