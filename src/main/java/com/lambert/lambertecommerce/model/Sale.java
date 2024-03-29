package com.lambert.lambertecommerce.model;

import com.lambert.lambertecommerce.helpers.constants.FieldSizes;
import com.lambert.lambertecommerce.helpers.constants.TemporalFormats;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Sales", uniqueConstraints = @UniqueConstraint(columnNames = {"_user", "product", "inserted_at"}))
public class Sale {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Min(FieldSizes.SALE_QUANTITY_MIN_VALUE)
   @Max(FieldSizes.SALE_QUANTITY_MAX_VALUE)
   @Column(name = "quantity")
   private Integer quantity;

   @ManyToOne
   @JoinColumn(name = "_user", nullable = false)
   private User user;

   @ManyToOne
   @JoinColumn(name = "product", nullable = false)
   private Product product;

   @DateTimeFormat(pattern = TemporalFormats.DATE_TIME_FORMAT)
   @Column(name = "inserted_at", nullable = false)
   private LocalDateTime insertedAt;

   @DateTimeFormat(pattern = TemporalFormats.DATE_TIME_FORMAT)
   @Column(name = "updated_at", nullable = false)
   private LocalDateTime updatedAt;

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Integer getQuantity() {
      return this.quantity;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }

   public User getUser() {
      return this.user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Product getProduct() {
      return this.product;
   }

   public void setProduct(Product product) {
      this.product = product;
   }

   public LocalDateTime getInsertedAt() {
      return this.insertedAt;
   }

   public void setInsertedAt(LocalDateTime insertedAt) {
      this.insertedAt = insertedAt;
   }

   public LocalDateTime getUpdatedAt() {
      return this.updatedAt;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
   }

   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
      if (this.updatedAt == null) {
         this.updatedAt = this.insertedAt;
      }
   }

   @PreUpdate
   public void preUpdate() {
      this.updatedAt = LocalDateTime.now();
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) return true;
      if (object == null || this.getClass() != object.getClass()) return false;
      Sale sale = (Sale) object;
      return Objects.equals(this.getId(), sale.getId()) || (Objects.equals(this.getUser(), sale.getUser()) && Objects.equals(this.getProduct(), sale.getProduct()) && Objects.equals(this.getInsertedAt(), sale.getInsertedAt()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUser(), this.getProduct(), this.getInsertedAt());
   }

   @Override
   public String toString() {
      return "Sale: {" +
              " id = " + this.getId().toString() +
              ", quantity = " + this.getQuantity().toString() +
              ", user = " + this.getUser().toString() +
              ", product = " + this.getProduct().toString() +
              ", insertedAt = " + this.getInsertedAt().toString() +
              ", updatedAt = " + this.getUpdatedAt().toString() +
              " }";
   }
}
