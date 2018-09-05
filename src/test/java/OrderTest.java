import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

   private SalesOrder salesOrder;

   @BeforeEach
   public void setup() {
      salesOrder = new SalesOrder(1075, BigDecimal.valueOf(47.45), BigDecimal.valueOf(31.79));
   }

   @Test
   @DisplayName("let: instance not be created if ID not greater than 0")
   public void shouldThrowIllegalArgExIfIdIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new Order(0L, "Commodities", Priority.LOW, salesOrder)
      );
   }

   @Test
   @DisplayName("let: instance not be created if ID is null")
   public void shouldThrowNullPointerExIfIdIsNull() {
      assertThrows(NullPointerException.class,
         () -> new Order(null, "Commodities", Priority.LOW, salesOrder)
      );
   }

   @Test
   @DisplayName("let: instance not be created if Category is NullOrBlank")
   public void shouldThrowIllegalArgExIfCategoryIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new Order(1L, null, Priority.LOW, salesOrder)
      );
   }

   @Test
   @DisplayName("let: instance not be created if Priority is null")
   public void shouldThrowNullPointerExIfPriorityIsNull() {
      assertThrows(NullPointerException.class,
         () -> new Order(1L, "Commodities", null, salesOrder)
      );
   }

   @Test
   @DisplayName("let: instance not be created if SalesOrder is null")
   public void shouldThrowNullPointerExIfSalesOrderIsNull() {
      assertThrows(NullPointerException.class,
         () -> new Order(1L, "Commodities", Priority.LOW, null)
      );
   }









}