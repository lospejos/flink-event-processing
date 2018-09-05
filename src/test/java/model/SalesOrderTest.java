package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SalesOrderTest {

   private SalesOrder salesOrder;

   @BeforeEach
   public void setup() {
      salesOrder = new SalesOrder(1075, BigDecimal.valueOf(47.45), BigDecimal.valueOf(31.79));
   }

   @Test
   @DisplayName("let: totalRevenue be calculated even if it's not passed in constructor")
   public void totalRevenueShouldBeComputedEvenIfNotProvided() {
      BigDecimal expectedValue = BigDecimal.valueOf(51008.75);
      Assertions.assertThat(salesOrder.getTotalRevenue()).isEqualTo(expectedValue);
   }

   @Test
   @DisplayName("let: totalCost be calculated even if it's not passed in constructor")
   public void totalCostShouldBeComputedEvenIfNotProvided() {
      BigDecimal expectedValue = BigDecimal.valueOf(34174.25);
      Assertions.assertThat(salesOrder.getTotalCost()).isEqualTo(expectedValue);
   }

   @Test
   @DisplayName("let: totalProfit be calculated even if it's not passed in constructor")
   public void totalProfitShouldBeComputedEvenIfNotProvided() {
      BigDecimal expectedValue = BigDecimal
         .valueOf(16834.50)
         .setScale(2, RoundingMode.CEILING);
      Assertions.assertThat(salesOrder.getTotalProfit()).isEqualTo(expectedValue);
   }

   @Test
   @DisplayName("let: instance not be created if Units Sold is not greater than 0")
   public void shouldThrowIllegalArgExIfUnitsSoldIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new SalesOrder(-1, BigDecimal.valueOf(47.45), BigDecimal.valueOf(31.79))
      );
   }

   @Test
   @DisplayName("let: instance not be created if Units Sold is Null")
   public void shouldThrowIllegalArgExIfUnitsSoldIsNull() {
      assertThrows(NullPointerException.class,
         () -> new SalesOrder(null, BigDecimal.valueOf(47.45), BigDecimal.valueOf(31.79))
      );
   }

   @Test
   @DisplayName("let: instance not be created if Unit Price is not greater than 0")
   public void shouldThrowIllegalArgExIfUnitPriceIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new SalesOrder(1075, BigDecimal.valueOf(-47.45), BigDecimal.valueOf(31.79))
      );
   }

   @Test
   @DisplayName("let: instance not be created if Units Price is Null")
   public void shouldThrowIllegalArgExIfUnitPriceIsNull() {
      assertThrows(NullPointerException.class,
         () -> new SalesOrder(1075, null, BigDecimal.valueOf(31.79))
      );
   }

   @Test
   @DisplayName("let: instance not be created if Unit Cost is not greater than 0")
   public void shouldThrowIllegalArgExIfUnitCostIsIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new SalesOrder(1075, BigDecimal.valueOf(47.45), BigDecimal.valueOf(-31.79))
      );
   }

   @Test
   @DisplayName("let: instance not be created if Units Cost is Null")
   public void shouldThrowIllegalArgExIfUnitCostIsIsNull() {
      assertThrows(NullPointerException.class,
         () -> new SalesOrder(1075, BigDecimal.valueOf(47.45), null)
      );
   }

}