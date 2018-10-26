package io.petproject.model;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Order {

   private Integer unitsSold;
   private BigDecimal unitPrice;
   private BigDecimal unitCost;
   private BigDecimal totalRevenue;
   private BigDecimal totalCost;
   private BigDecimal totalProfit;
   private OrderMetadata metadata;

   public static class Builder {
      private Long id;
      private Integer unitsSold;
      private BigDecimal unitPrice;
      private BigDecimal unitCost;
      private BigDecimal totalRevenue;
      private BigDecimal totalCost;
      private BigDecimal totalProfit;
      private String category;
      private Priority priority;

      public Builder id(Long id) {
         this.id = id;
         return this;
      }

      public Builder category(String category) {
         this.category = category;
         return this;
      }

      public Builder priority(Priority priority) {
         this.priority = priority;
         return this;
      }

      public Builder unitsSold(Integer unitsSold) {
         this.unitsSold = unitsSold;
         return this;
      }

      public Builder unitPrice(BigDecimal unitPrice) {
         this.unitPrice = unitPrice;
         return this;
      }

      public Builder unitCost(BigDecimal unitCost) {
         this.unitCost = unitCost;
         return this;
      }

      public Builder totalRevenue(BigDecimal totalRevenue) {
         this.totalRevenue = totalRevenue;
         return this;
      }

      public Builder totalCost(BigDecimal totalCost) {
         this.totalCost = totalCost;
         return this;
      }

      public Builder totalProfit(BigDecimal totalProfit) {
         this.totalProfit = totalProfit;
         return this;
      }

      public Order build() {
         return new Order(unitsSold, unitPrice, unitCost, totalRevenue, totalCost, totalProfit,
            new OrderMetadata(id, category, priority));
      }

   }

   private Order(Integer unitsSold, BigDecimal unitPrice, BigDecimal unitCost,
                 BigDecimal totalRevenue, BigDecimal totalCost, BigDecimal totalProfit,
                 OrderMetadata metadata) {
      setUnitsSold(unitsSold);
      setUnitPrice(unitPrice);
      setUnitCost(unitCost);
      setTotalRevenue(totalRevenue);
      setTotalCost(totalCost);
      setTotalProfit(totalProfit);
      setMetadata(metadata);
   }

   public Integer getUnitsSold() {
      return unitsSold;
   }

   public BigDecimal getUnitPrice() {
      return unitPrice;
   }

   public BigDecimal getUnitCost() {
      return unitCost;
   }

   public BigDecimal getTotalRevenue() {
      return totalRevenue;
   }

   public BigDecimal getTotalCost() {
      return totalCost;
   }

   public BigDecimal getTotalProfit() {
      return totalProfit;
   }

   public OrderMetadata getMetadata() {
      return metadata;
   }

   private void setUnitsSold(Integer unitsSold) {
      checkNotNull(unitsSold, "Units Sold cannot be null");
      checkArgument(unitsSold > 0, "Units Sold must be greater than 0");
      this.unitsSold = unitsSold;
   }

   private void setUnitPrice(BigDecimal unitPrice) {
      checkNotNull(unitPrice, "Unit Price cannot be null");
      checkArgument(unitPrice.compareTo(BigDecimal.ZERO) > 0, "Unit Price must be greater than 0");
      this.unitPrice = unitPrice;
   }

   private void setUnitCost(BigDecimal unitCost) {
      checkNotNull(unitCost, "Unit Cost cannot be null");
      checkArgument(unitCost.compareTo(BigDecimal.ZERO) > 0, "Unit Cost must be greater than 0");
      this.unitCost = unitCost;
   }

   private void setTotalRevenue(BigDecimal totalRevenue) {
      if (totalRevenue == null) {
         this.totalRevenue = getUnitPrice().multiply(BigDecimal.valueOf(getUnitsSold()));
      } else {
         this.totalRevenue = totalRevenue;
      }
   }

   private void setTotalCost(BigDecimal totalCost) {
      if (totalCost == null) {
         this.totalCost = getUnitCost().multiply(BigDecimal.valueOf(getUnitsSold()));
      } else {
         this.totalCost = totalCost;
      }
   }

   private void setTotalProfit(BigDecimal totalProfit) {
      if (totalProfit == null) {
         this.totalProfit = getTotalRevenue().subtract(getTotalCost());
      } else {
         this.totalProfit = totalProfit;
      }
   }

   private void setMetadata(OrderMetadata metadata) {
      this.metadata = metadata;
   }

}
