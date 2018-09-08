package io.petproject.service;

import io.petproject.model.Order;
import io.petproject.model.Priority;
import io.petproject.model.SalesOrder;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaServiceTest {

   private final static String KAFKA_TEST_TOPIC = "kafka-test-topic";
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
   @DisplayName("let: publish/subscribe send and receive messages to/from Kafka")
   public void shouldPublishToAndConsumeFromKafka() throws Exception {
      KafkaService<Order> kafkaService = new KafkaService<>();
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

      future.orTimeout(5, TimeUnit.SECONDS);
      Thread.sleep(5000);
      return outputStream;
   }

}