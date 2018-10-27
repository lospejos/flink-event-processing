package io.petproject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest {

   private OrderRepository repo;

   @BeforeEach
   public void setup() {
      var file = new File(getClass().getResource("/100K-Sales-Records.csv").getFile());
      repo = new OrderRepository();
      repo.process(file, ",");
   }

   @Test
   @DisplayName("when a valid region is provided, return a non-empty OrderMetadata list")
   public void findOrdersByRegionShouldReturnNonEmptyListIfRegionIsValid() throws Exception {
      var europeanOrders = repo.findOrdersByRegion("europe");
      assertThat(europeanOrders.size()).isEqualTo(25877);
   }

   @Test
   @DisplayName("when an invalid region is provided, return an empty OrderMetadata list")
   public void findOrdersByRegionShouldReturnEmptyListIfRegionIsInvalid() throws Exception {
      var europeanOrders = repo.findOrdersByRegion("invalidRegion");
      assertThat(europeanOrders.size()).isEqualTo(0);
   }

}