package io.petproject.service;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchemaWrapper;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchemaWrapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class KafkaService<T> {

   private StreamExecutionEnvironment streamEnv;
   private Properties kafkaConfig;
   private Class<T> tClass;

   public KafkaService(Class<T> tClass, Properties kafkaConfig) {
      this.streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
      this.kafkaConfig = kafkaConfig;
      this.tClass = tClass; // TODO: Workaround to deal with generics type erasure
   }

   public void publish(String kafkaTopic, Collection<T> collection) throws Exception {
      var dataStream = streamEnv.fromCollection(collection);
      dataStream.addSink(getKafkaProducer(kafkaTopic));
      streamEnv.execute();
   }

   public DataStream<T> subscribe(String kafkaTopic) {
      return streamEnv.addSource(getKafkaConsumer(kafkaTopic));
   }

   private FlinkKafkaProducer011<T> getKafkaProducer(String kafkaTopic) {
      var kafkaProducer = new FlinkKafkaProducer011<>(
         kafkaTopic,
         new KeyedSerializationSchemaWrapper<>(getSerializationSchema()),
         getProducerProperties(),
         FlinkKafkaProducer011.Semantic.AT_LEAST_ONCE
      );
      kafkaProducer.setWriteTimestampToKafka(true);
      return kafkaProducer;
   }

   private FlinkKafkaConsumer011<T> getKafkaConsumer(String kafkaTopic) {
      var kafkaConsumer = new FlinkKafkaConsumer011<>(
         kafkaTopic,
         new KeyedDeserializationSchemaWrapper<>(getSerializationSchema()),
         getConsumerProperties()
      );
      kafkaConsumer.setStartFromGroupOffsets();
      return kafkaConsumer;
   }

   private TypeInformationSerializationSchema<T> getSerializationSchema() {
      return new TypeInformationSerializationSchema<>(TypeInformation.of(tClass), new ExecutionConfig());
   }

   private Properties getProducerProperties() {
      Properties kafkaProducerProps = new Properties();
      kafkaProducerProps.putAll(Map.of(
         ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getProperty("kafka.producer.bootstrap-servers")
      ));
      return kafkaProducerProps;
   }

   private Properties getConsumerProperties() {
      Properties kafkaConsumerProps = new Properties();
      kafkaConsumerProps.putAll(Map.of(
         ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getProperty("kafka.consumer.bootstrap-servers"),
         ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getProperty("kafka.consumer.group-id"),
         ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true
      ));
      return kafkaConsumerProps;
   }

}
