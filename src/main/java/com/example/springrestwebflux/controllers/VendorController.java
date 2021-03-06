package com.example.springrestwebflux.controllers;

import com.example.springrestwebflux.domain.Vendor;
import com.example.springrestwebflux.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

  @PostMapping("/api/v1/vendors")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createNewVendor(@RequestBody Publisher<Vendor> vendors) {

    return vendorRepository.saveAll(vendors).then();
  }

  @PutMapping("/api/v1/vendors/{id}")
  public Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {

    vendor.setId(id);
    return vendorRepository.save(vendor);
  }
}
