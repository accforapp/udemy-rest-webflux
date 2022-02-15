package com.example.springrestwebflux.bootstrap;

import com.example.springrestwebflux.domain.Category;
import com.example.springrestwebflux.domain.Vendor;
import com.example.springrestwebflux.repositories.CategoryRepository;
import com.example.springrestwebflux.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoadData implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final VendorRepository vendorRepository;

  public LoadData(CategoryRepository categoryRepository, VendorRepository vendorRepository) {

    this.categoryRepository = categoryRepository;
    this.vendorRepository = vendorRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadCategories();
    loadVendors();
  }

  private void loadCategories() {

    if (categoryRepository.count().block() == 0) {

      Category fruit = new Category();
      fruit.setDescription("Fruit");

      categoryRepository.save(fruit).block();

      Category juice = new Category();
      juice.setDescription("Juice");

      categoryRepository.save(juice).block();

      Category tea = new Category();
      tea.setDescription("Tea");

      categoryRepository.save(tea).block();

      Category coffee = new Category();
      coffee.setDescription("Coffee");

      categoryRepository.save(coffee).block();

      Category bread = new Category();
      bread.setDescription("Bread");

      categoryRepository.save(bread).block();

      Category milk = new Category();
      milk.setDescription("Milk");

      categoryRepository.save(milk).block();
    }

    log.info("Categories count: " + categoryRepository.count().block());
  }

  private void loadVendors() {

    if (vendorRepository.count().block() == 0) {

      Vendor vendor1 = new Vendor();
      vendor1.setFirstName("Joe");
      vendor1.setLastName("Tribiani");

      vendorRepository.save(vendor1).block();

      Vendor vendor2 = new Vendor();
      vendor2.setFirstName("Chandler");
      vendor2.setLastName("Bing");

      vendorRepository.save(vendor2).block();

      Vendor vendor3 = new Vendor();
      vendor3.setFirstName("Ross");
      vendor3.setLastName("Gellar");

      vendorRepository.save(vendor3).block();

      Vendor vendor4 = new Vendor();
      vendor4.setFirstName("Monica");
      vendor4.setLastName("Gellar");

      vendorRepository.save(vendor4).block();

      Vendor vendor5 = new Vendor();
      vendor5.setFirstName("Raychel");
      vendor5.setLastName("Green");

      vendorRepository.save(vendor5).block();

      Vendor vendor6 = new Vendor();
      vendor6.setFirstName("Fibbi");
      vendor6.setLastName("???");

      vendorRepository.save(vendor6).block();
    }

    log.info("Vendors count: " + vendorRepository.count().block());
  }
}
