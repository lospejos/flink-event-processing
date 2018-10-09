package io.petproject.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

   private Order salesOrder;

   @BeforeEach
   public void setup() {
      salesOrder = new Order.Builder()
         .id(1L)
         .category("Commodities")
         .priority(Priority.HIGH)
         .unitsSold(1075)
         .unitPrice(BigDecimal.valueOf(47.45))
         .unitCost(BigDecimal.valueOf(31.79))
         .build();
   }

   @Test
   @DisplayName("when totalRevenue is not in the constructor, compute it based on unitsSold and unitsPrice")
   public void totalRevenueShouldBeComputedEvenIfNotProvided() {
      BigDecimal expectedValue = BigDecimal.valueOf(51008.75);
      assertThat(salesOrder.getTotalRevenue()).isEqualTo(expectedValue);
   }

   @Test
   @DisplayName("when totalCost is not in the constructor, compute it based on unitsSold and unitsCost")
   public void totalCostShouldBeComputedEvenIfNotProvided() {
      BigDecimal expectedValue = BigDecimal.valueOf(34174.25);
      assertThat(salesOrder.getTotalCost()).isEqualTo(expectedValue);
   }

   @Test
   @DisplayName("when totalProfit is not in the constructor, compute it based on totalRevenue and totalCost")
   public void totalProfitShouldBeComputedEvenIfNotProvided() {
      BigDecimal expectedValue = BigDecimal
         .valueOf(16834.50)
         .setScale(2, RoundingMode.CEILING);
      assertThat(salesOrder.getTotalProfit()).isEqualTo(expectedValue);
   }

   @Test
   @DisplayName("when unitsSold is < 0, throw IllegalArgEx")
   public void shouldThrowIllegalArgExIfUnitsSoldIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new Order.Builder()
            .id(1L)
            .category("Commodities")
            .priority(Priority.HIGH)
            .unitsSold(-1)
            .unitPrice(BigDecimal.valueOf(47.45))
            .unitCost(BigDecimal.valueOf(31.79))
            .build()
      );
   }

   @Test
   @DisplayName("when unitsSold is null, throw NullPointerEx")
   public void shouldThrowNullPointerExIfUnitsSoldIsNull() {
      assertThrows(NullPointerException.class,
         () -> new Order.Builder()
            .id(1L)
            .category("Commodities")
            .priority(Priority.HIGH)
            .unitsSold(null)
            .unitPrice(BigDecimal.valueOf(47.45))
            .unitCost(BigDecimal.valueOf(31.79))
            .build()
      );
   }

   @Test
   @DisplayName("when unitPrice is < 0, throw IllegalArgEx")
   public void shouldThrowIllegalArgExIfUnitPriceIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new Order.Builder()
            .id(1L)
            .category("Commodities")
            .priority(Priority.HIGH)
            .unitsSold(1)
            .unitPrice(BigDecimal.valueOf(-47.45))
            .unitCost(BigDecimal.valueOf(31.79))
            .build()
      );
   }

   @Test
   @DisplayName("when unitPrice is null, throw NullPointerEx")
   public void shouldThrowNullPointerExIfUnitPriceIsNull() {
      assertThrows(NullPointerException.class,
         () -> new Order.Builder()
            .id(1L)
            .category("Commodities")
            .priority(Priority.HIGH)
            .unitsSold(1)
            .unitPrice(null)
            .unitCost(BigDecimal.valueOf(31.79))
            .build()
      );
   }

   @Test
   @DisplayName("when unitCost is < 0, throw IllegalArgEx")
   public void shouldThrowIllegalArgExIfUnitCostIsIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new Order.Builder()
            .id(1L)
            .category("Commodities")
            .priority(Priority.HIGH)
            .unitsSold(1)
            .unitPrice(BigDecimal.valueOf(47.45))
            .unitCost(BigDecimal.valueOf(-31.79))
            .build()
      );
   }

   @Test
   @DisplayName("when unitCost is null, throw NullPointerEx")
   public void shouldThrowNullPointerExIfUnitCostIsIsNull() {
      assertThrows(NullPointerException.class,
         () -> new Order.Builder()
            .id(1L)
            .category("Commodities")
            .priority(Priority.HIGH)
            .unitsSold(1)
            .unitPrice(BigDecimal.valueOf(47.45))
            .unitCost(null)
            .build()
      );
   }

}