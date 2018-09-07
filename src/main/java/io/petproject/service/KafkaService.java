package io.petproject.service;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Collection;

public class KafkaService<T> {

   private final static String KAFKA_SERVER = "localhost:9092";
   private StreamExecutionEnvironment sEnv;


   public KafkaService() {
      this.sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
   }

   public void push(String kafkaTopic, Collection<T> collection) throws Exception {
      DataStream<T> dataStream = sEnv.fromCollection(collection);
      FlinkKafkaProducer011<T> kafkaProducer = getKafkaProducer(kafkaTopic);
      dataStream.addSink(kafkaProducer);
      sEnv.execute();
   }

   private FlinkKafkaProducer011<T> getKafkaProducer(String kafkaTopic) {
      FlinkKafkaProducer011<String> kafkaProducer = new FlinkKafkaProducer011<>(KAFKA_SERVER, kafkaTopic, new SimpleStringSchema());
      return kafkaProducer;
   }

}
