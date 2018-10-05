package io.petproject.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderMetadataTest {

   @Test
   @DisplayName("when ID is lower than or equalTo 0, throw IllegalArgEx")
   public void shouldThrowIllegalArgExIfIdIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new OrderMetadata(0L, "Commodities", Priority.LOW)
      );
   }

   @Test
   @DisplayName("when ID is null, throw IllegalArgEx")
   public void shouldThrowNullPointerExIfIdIsNull() {
      assertThrows(NullPointerException.class,
         () -> new OrderMetadata(null, "Commodities", Priority.LOW)
      );
   }

   @Test
   @DisplayName("when Category is null or blank, throw IllegalArgEx")
   public void shouldThrowIllegalArgExIfCategoryIsInvalid() {
      assertThrows(IllegalArgumentException.class,
         () -> new OrderMetadata(1L, null, Priority.LOW)
      );
   }

   @Test
   @DisplayName("when Priority is null, throw NullPointerEx")
   public void shouldThrowNullPointerExIfPriorityIsNull() {
      assertThrows(NullPointerException.class,
         () -> new OrderMetadata(1L, "Commodities", null)
      );
   }

}