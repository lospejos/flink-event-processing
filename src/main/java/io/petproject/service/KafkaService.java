package io.petproject.service;

import io.petproject.model.Order;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Collection;
import java.util.Properties;

public class KafkaService<T> {

   private final static String KAFKA_SERVER = "localhost:9092";
   private final static String ZOOKEEPER_SERVER = "localhost:2181";
   private final static String CONSUMER_GROUP_ID = "test-flink-consumer";

   private StreamExecutionEnvironment streamEnv;
   private Class<T> tClass;

   public KafkaService(Class<T> tClass) {
      this.streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
      this.tClass = tClass; // TODO: Workaround to deal with generics type erasure
   }

   public void publish(String kafkaTopic, Collection<T> collection) throws Exception {
      DataStream<T> dataStream = streamEnv.fromCollection(collection);
      dataStream.addSink(getKafkaProducer(kafkaTopic));
      streamEnv.execute();
   }

   public DataStream<T> subscribe(String kafkaTopic) {
      DataStream<T> dataStream = streamEnv.addSource(getKafkaConsumer(kafkaTopic));
      return dataStream;
   }

   protected FlinkKafkaProducer011<T> getKafkaProducer(String kafkaTopic) {
      FlinkKafkaProducer011<T> kafkaProducer = new FlinkKafkaProducer011<>(KAFKA_SERVER, kafkaTopic, getSerializationSchema());
      kafkaProducer.setWriteTimestampToKafka(true);
      return kafkaProducer;
   }

   protected FlinkKafkaConsumer011<T> getKafkaConsumer(String kafkaTopic) {
      FlinkKafkaConsumer011<T> kafkaConsumer = new FlinkKafkaConsumer011<>(kafkaTopic, getSerializationSchema(), getKafkaConsumerProperties());
      kafkaConsumer.setStartFromGroupOffsets();
      return kafkaConsumer;
   }

   private TypeInformationSerializationSchema<T> getSerializationSchema() {
      return new TypeInformationSerializationSchema<>(
         TypeInformation.of(tClass),
         new ExecutionConfig()
      );
   }

   private Properties getKafkaConsumerProperties() {
      Properties kafkaConsumer = new Properties();
      kafkaConsumer.setProperty("bootstrap.servers", KAFKA_SERVER);
      kafkaConsumer.setProperty("zookeeper.connect", ZOOKEEPER_SERVER);
      kafkaConsumer.setProperty("group.id", CONSUMER_GROUP_ID);
      return kafkaConsumer;
   }

   protected StreamExecutionEnvironment getStreamEnv() {
      return streamEnv;
   }
}
