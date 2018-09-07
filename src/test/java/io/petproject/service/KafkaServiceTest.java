package io.petproject.service;

import io.petproject.model.Order;
import io.petproject.model.Priority;
import io.petproject.model.SalesOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class KafkaServiceTest {

   private final static String KAKFA_TOPIC = "kafka-test-topic";
   private List<Order> orders;

   @BeforeEach
   public void setup() {
      orders = List.of(
         new Order(1L, "Meat", Priority.HIGH,
            new SalesOrder(2431, BigDecimal.valueOf(421.89), BigDecimal.valueOf(364.69))
         ),
         new Order(3L, "Beverages", Priority.MEDIUM,
            new SalesOrder(2617, BigDecimal.valueOf(47.45), BigDecimal.valueOf(31.79))
         ),
         new Order(2L, "Clothes", Priority.LOW,
            new SalesOrder(9527, BigDecimal.valueOf(109.28), BigDecimal.valueOf(35.84))
         ),
         new Order(4L, "Eletronics", Priority.HIGH,
            new SalesOrder(3345, BigDecimal.valueOf(651.21), BigDecimal.valueOf(524.96))
         )
      );
   }

   @Test
   public void shouldPublishOrdersToKafka() throws Exception {
      KafkaService<Order> kafkaService = new KafkaService<>();
      kafkaService.publish(KAKFA_TOPIC, orders);
   }

}