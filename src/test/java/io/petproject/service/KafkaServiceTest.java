package io.petproject.service;

import io.petproject.model.Order;
import io.petproject.repository.OrderRepository;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class KafkaServiceTest {

   private final static String KAFKA_TEST_TOPIC = "sales-orders";
   private Properties kafkaConfig;
   private List<Order> orders;

   @BeforeEach
   public void setup() throws Exception {
      var file = new File(getClass().getResource("/100K-Sales-Records.csv").getFile());
      var repo = new OrderRepository();
      repo.process(file, ",");
      orders = repo.findOrdersByRegion("europe");

      kafkaConfig = new Properties();
      kafkaConfig.setProperty("kafka.producer.bootstrap-servers", "localhost:9092");
      kafkaConfig.setProperty("kafka.consumer.bootstrap-servers", "localhost:9092");
      kafkaConfig.setProperty("kafka.consumer.group-id", "test-consumer-group");
   }

   @Test
   @Disabled("Setup an Embedded Kafka Environment for testing")
   @DisplayName("when publishing a dataStream, it should consume the same amount or higher of messages")
   public void shouldPublishToAndConsumeFromKafka() throws Exception {
      var kafkaService = new KafkaService<>(Order.class, kafkaConfig);
      kafkaService.publish(KAFKA_TEST_TOPIC, orders);
      DataStream<Order> consumerStream = kafkaService.subscribe(KAFKA_TEST_TOPIC);

      ByteArrayOutputStream outputStream = readStreamFor(consumerStream, 10);
      assertThat(outputStream.toString().split("\n").length)
         .isGreaterThanOrEqualTo(this.orders.size());
   }

   private ByteArrayOutputStream readStreamFor(DataStream<Order> consumerStream, int timeInSeconds) throws InterruptedException {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outputStream));
      System.setErr(new PrintStream(new ByteArrayOutputStream()));
      consumerStream.print(); // Sink operation;

      CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
         try {
            consumerStream.getExecutionEnvironment().execute();
         } catch (Exception e) {
            e.printStackTrace();
         }
      });

      future.orTimeout(timeInSeconds, TimeUnit.SECONDS);
      TimeUnit.SECONDS.sleep(timeInSeconds);
      return outputStream;
   }

}