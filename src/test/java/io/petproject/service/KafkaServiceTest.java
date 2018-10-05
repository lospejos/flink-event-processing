package io.petproject.service;

import io.petproject.model.Order;
import io.petproject.model.Priority;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaServiceTest {

   private final static String KAFKA_TEST_TOPIC = "kafka-test-topic";
   private List<Order> orders;

   @BeforeEach
   public void setup() {
      orders = List.of(
         new Order.Builder()
            .id(1L).category("Meat").priority(Priority.HIGH)
            .unitsSold(2431).unitPrice(BigDecimal.valueOf(421.89)).unitCost(BigDecimal.valueOf(364.69))
            .build(),
         new Order.Builder()
            .id(2L).category("Beverages").priority(Priority.MEDIUM)
            .unitsSold(2617).unitPrice(BigDecimal.valueOf(47.45)).unitCost(BigDecimal.valueOf(31.79))
            .build(),
         new Order.Builder()
            .id(3L).category("Clothes").priority(Priority.LOW)
            .unitsSold(9527).unitPrice(BigDecimal.valueOf(109.28)).unitCost(BigDecimal.valueOf(35.84))
            .build(),
         new Order.Builder()
            .id(4L).category("Eletronics").priority(Priority.LOW)
            .unitsSold(3345).unitPrice(BigDecimal.valueOf(651.21)).unitCost(BigDecimal.valueOf(524.96))
            .build()
      );
   }

   @Test
   @Disabled("Setup an Embedded Kafka Environment for testing")
   @DisplayName("when publishing a dataStream, it should consume the same amount or higher of messages")
   public void shouldPublishToAndConsumeFromKafka() throws Exception {
      var kafkaService = new KafkaService<>(Order.class);
      kafkaService.publish(KAFKA_TEST_TOPIC, orders);

      DataStream<Order> consumerStream = kafkaService.subscribe(KAFKA_TEST_TOPIC);
      consumerStream.print();
      ByteArrayOutputStream sysOutReturn = setupSysOutToCapturePrint(kafkaService);

      assertThat(sysOutReturn.toString().split("\n").length)
         .isGreaterThanOrEqualTo(orders.size());
   }

   private ByteArrayOutputStream setupSysOutToCapturePrint(KafkaService<?> kafkaService) throws InterruptedException {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStream));
      System.setErr(new PrintStream(new ByteArrayOutputStream()));

      CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
         try {
            kafkaService.getStreamEnv().execute();
         } catch (Exception e) {
            e.printStackTrace();
         }
      });

      future.orTimeout(10, TimeUnit.SECONDS);
      Thread.sleep(10000);
      return outputStream;
   }

}