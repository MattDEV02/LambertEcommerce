package com.market.marketnexus.service;

import com.market.marketnexus.helpers.sale.Utils;
import com.market.marketnexus.model.Product;
import com.market.marketnexus.model.ProductCategory;
import com.market.marketnexus.model.Sale;
import com.market.marketnexus.model.User;
import com.market.marketnexus.repository.ProductCategoryRepository;
import com.market.marketnexus.repository.ProductRepository;
import com.market.marketnexus.repository.SaleRepository;
import com.market.marketnexus.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SaleService {
   // TODO: ASSOCIAZIONE USER E SALE BIDIREZIONALE E CASCADE TRA SALE E PRODUCT
   @Autowired
   protected SaleRepository saleRepository;
   @Autowired
   protected ProductRepository productRepository;
   @Autowired
   protected ProductCategoryRepository productCategoryRepository;
   @Autowired
   protected UserRepository userRepository;

   @Transactional
   public Sale saveSale(@NotNull Sale sale, User user, Product product) {
      sale.setUser(user);
      sale.setProduct(product);
      Float salePrice = this.calculateSalePrice(sale);
      sale.setSalePrice(salePrice);
      return this.saleRepository.save(sale);
   }

   @Transactional
   public Sale saveSale(Sale sale) {
      Float salePrice = this.calculateSalePrice(sale);
      sale.setSalePrice(salePrice);
      Sale savedSale = this.saleRepository.save(sale);
      return savedSale;
   }

   public Sale getSale(Long id) {
      Optional<Sale> result = this.saleRepository.findById(id);
      return result.orElse(null);
   }

   public Set<Sale> getAllSalesByUser(User user) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.saleRepository.findAllByUser(user);
      for (Sale sale : sales) {
         if (!sale.getIsSold()) {
            result.add(sale);
         }
      }
      return result;
   }

   public Set<Sale> getAllSales() {
      Set<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAt();
      return sales;
   }

   public Set<Sale> getAllSalesByProductName(String productName) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAt();
      Set<Product> products = this.productRepository.findAllByNameContainingIgnoreCase(productName);
      for (Sale sale : sales) {
         if (!sale.getIsSold() && products.contains(sale.getProduct())) {
            result.add(sale);
         }
      }
      return result;
   }

   public Set<Sale> getAllSalesByProductCategoryId(Long productCategoryId) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAt();
      ProductCategory productCategory = this.productCategoryRepository.findById(productCategoryId).orElse(null);
      Set<Product> products = this.productRepository.findAllByCategory(productCategory);
      for (Sale sale : sales) {
         if (!sale.getIsSold() && products.contains(sale.getProduct())) {
            result.add(sale);
         }
      }
      return result;
   }

   public Set<Sale> getAllSalesByProductNameAndProductCategoryId(String productName, Long productCategoryId) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.saleRepository.findAllByOrderByUpdatedAt();
      ProductCategory productCategory = this.productCategoryRepository.findById(productCategoryId).orElse(null);
      Set<Product> products = this.productRepository.findAllByNameContainingIgnoreCaseAndCategory(productName, productCategory);
      for (Sale sale : sales) {
         if (!sale.getIsSold() && products.contains(sale.getProduct())) {
            result.add(sale);
         }
      }
      return result;
   }

   public Set<Sale> getAllUserSoldSales(User user) {
      Set<Sale> result = new HashSet<Sale>();
      Set<Sale> sales = this.saleRepository.findAllByUser(user);
      for (Sale sale : sales) {
         if (sale.getIsSold()) {
            result.add(sale);
         }
      }
      return result;
   }

   public List<Object[]> countCurrentWeekUserSoldSales(@NotNull Long userId) {
      return this.saleRepository.countCurrentWeekUserSales(userId);
   }

   public Float calculateSalePrice(@NotNull Sale sale) {
      return Utils.roundNumberTo2Decimals(sale.getProduct().getPrice() * sale.getQuantity());
   }
}
