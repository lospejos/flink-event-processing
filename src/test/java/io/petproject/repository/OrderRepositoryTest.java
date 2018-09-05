package io.petproject.repository;

import io.petproject.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest {

   private OrderRepository repo;
   private File file;

   @BeforeEach
   public void setup() {
      file = new File(getClass().getResource("/10K-Sales-Records.csv").getFile());
      repo = new OrderRepository();
   }

   @Test
   public void processShouldReturnOrderDataSet() throws Exception {
      repo.process(file, ",");
      List<Order> europeanOrders = repo.findOrdersByRegion("europe");
      assertThat(europeanOrders.size()).isEqualTo(2633);
   }

}